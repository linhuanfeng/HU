package com.hu.health.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("thread.pool.config")
@Data
public class ThreadPoolProperties {
    private int coreSize;
    private int maximumPoolSize;
    private int keepAliveTime;
    private int blockQueueSize;
}
