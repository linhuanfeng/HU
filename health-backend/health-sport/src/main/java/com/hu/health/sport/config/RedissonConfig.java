package com.hu.health.sport.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Bean
    public RedissonClient redissonClient(){
        // 配值文件
        Config config = new Config();
        SingleServerConfig singleServer = config.useSingleServer();
        singleServer.setTimeout(timeout);
        singleServer.setAddress("redis://"+host+":"+port);

        return Redisson.create(config);
    }
}
