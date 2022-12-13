package com.hu.health.community.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Rabbitmq配置：
 *  使用Jackson作为消息转换器
 *  开启生产者回调机制：ConfirmCallback和ReturnCallback
 */
@Configuration
@Slf4j
public class MqConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;



    @PostConstruct //依赖注入成功后执行
    public void setConfirmCallback(){
        /**
         * 消息到达broker代理的回调
         */
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause)->{
            if(ack){
                log.info("消息投递到交换机成功：[correlationData={}]",correlationData);
            } else {
                log.error("消息投递到交换机失败：[correlationData={}，原因：{}]", correlationData, cause);
            }
        });

        /**
         * 消息到达队列
         */
        rabbitTemplate.setReturnCallback((message,replyCode,replyText,exchange,routingKey)->{
            log.error("路由到队列失败，消息内容：{}，交换机：{}，路由件：{}，回复码：{}，回复文本：{}", message, exchange, routingKey, replyCode, replyText);
        });
    }
}
