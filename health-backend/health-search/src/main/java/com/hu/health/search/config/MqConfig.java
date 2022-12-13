package com.hu.health.search.config;

import com.hu.health.common.MqConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MqConfig {
    /**
     * 监听帖子的话题交换机
     * @return
     */
    @Bean
    public Exchange QuestionExchange(){
        return new TopicExchange(
                MqConstants.QUESTION_EXCHANGE,
                true,
                false,
                null
        );
    }

    /**
     * 监听帖子更新的队列
     * @return
     */
    @Bean
    public Queue QuestionUpdateQueue(){
        return new Queue(
                MqConstants.QUESTION_UPDATE_QUEUE,
                true,
                false,
                false,
                null
        );
    }

    /**
     * 监听帖子删除的队列
     * @return
     */
    @Bean
    public Queue QuestionDeleteQueue(){
        return new Queue(
                MqConstants.QUESTION_DELETE_QUEUE,
                true,
                false,
                false,
                null
        );
    }

    /**
     * 监听帖子更新的绑定
     * @return
     */
    @Bean
    public Binding QuestionUpdateBinding(){
        return new Binding(
                MqConstants.QUESTION_UPDATE_QUEUE,
                Binding.DestinationType.QUEUE,
                MqConstants.QUESTION_EXCHANGE,
                MqConstants.QUESTION_UPDATE_ROUTINGKEY,
                null
        );
    }
    /**
     * 监听帖子删除的绑定
     * @return
     */
    @Bean
    public Binding CommentQuestionBinding(){
        return new Binding(
                MqConstants.QUESTION_DELETE_QUEUE,
                Binding.DestinationType.QUEUE,
                MqConstants.QUESTION_EXCHANGE,
                MqConstants.QUESTION_DELETE_ROUTINGKEY,
                null
        );
    }
}
