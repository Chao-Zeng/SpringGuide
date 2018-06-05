package com.charles.SpringBootGuide.message;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class MessageRepository {

    @Resource(name = "redisTemplate")
    StringRedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<String, Message> valueOperations;

    void saveMessage(Message message) {
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set(String.valueOf(message.getId()), message);
                return operations.exec();
            }
        });
    }

    Message getMessage(Integer messageId) {
        return valueOperations.get(String.valueOf(messageId));
    }
}
