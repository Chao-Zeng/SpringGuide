package com.charles.SpringBootGuide.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ConfigurationProperties
@Validated
public class PropertyConfig {

    @NotEmpty
    private  String redisIp;

    @NotNull
    private  int redisPort;

    public String getRedisIp() {
        return redisIp;
    }

    public void setRedisIp(String redisIp) {
        this.redisIp = redisIp;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(short redisPort) {
        this.redisPort = redisPort;
    }
}
