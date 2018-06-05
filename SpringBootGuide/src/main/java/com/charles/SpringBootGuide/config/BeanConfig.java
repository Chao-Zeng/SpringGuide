package com.charles.SpringBootGuide.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.time.Duration;
import java.util.Optional;

@Configuration
@Import({PropertyConfig.class})
public class BeanConfig {

    private final PropertyConfig propertyConfig;

    @Autowired
    public BeanConfig(PropertyConfig propertyConfig) {
        this.propertyConfig = propertyConfig;
    }

    /**
     * jedis, not thread safe. need use connection pool in multi-thread env
     */
//    @Bean
//    public RedisConnectionFactory jedisConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master(propertyConfig.getRedisMasterName())
//                .sentinel(propertyConfig.getRedisIp(), propertyConfig.getRedisPort());
//
//        return new JedisConnectionFactory(sentinelConfig);
//    }

    /**
     * lettuce, base netty, non-blocking, thread safe
     */
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        return  new LettuceConnectionFactory(new RedisStandaloneConfiguration(propertyConfig.getRedisIp(), propertyConfig.getRedisPort()));
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
