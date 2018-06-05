package com.charles.SpringBootGuide.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class BeanConfig {

    private final PropertyConfig propertyConfig;

    @Autowired
    public BeanConfig(PropertyConfig propertyConfig) {
        this.propertyConfig = propertyConfig;
    }

    /**
     * jedis, not thread safe. need use connection pool in multi-thread env
     */
    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel(propertyConfig.getRedisIp(), propertyConfig.getRedisPort());
        return new JedisConnectionFactory(sentinelConfig);
    }

    /**
     * lettuce, base netty, non-blocking, thread safe
     */
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel(propertyConfig.getRedisIp(), propertyConfig.getRedisPort());
        return  new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return  lettuceConnectionFactory();
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return  template;
    }
}
