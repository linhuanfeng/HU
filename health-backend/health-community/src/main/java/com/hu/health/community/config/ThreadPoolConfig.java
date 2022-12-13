package com.hu.health.community.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfig {
    /**
     * int corePoolSize,
     * int maximumPoolSize,
     * long keepAliveTime,
     * TimeUnit unit,
     * BlockingQueue<Runnable> workQueue,
     * ThreadFactory threadFactory,
     * RejectedExecutionHandler com.hu.health.common.handler
     * @return
     */
//    @Bean
//    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolProperties properties){
//
//        return new ThreadPoolExecutor(
//                properties.getCoreSize(),
//                properties.getMaximumPoolSize(),
//                properties.getKeepAliveTime(),
//                TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>(properties.getBlockQueueSize()),
//                new ThreadPoolExecutor.DiscardOldestPolicy()); // 丢弃最老的任务
//    }
    @Bean
    public ScheduledExecutorService scheduledThreadPoolExecutor(ThreadPoolProperties properties){
        return Executors.newScheduledThreadPool(properties.getCoreSize());
    }
}
