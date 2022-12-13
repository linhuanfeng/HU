package com.hu.health.member.config;

import com.hu.health.common.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
     * 监听帖子评论的队列
     * @return
     */
    @Bean
    public Queue QuestionCommentQueue(){
        return new Queue(
                MqConstants.QUESTION_COMMENT_QUEUE,
                true,
                false,
                false,
                null
        );
    }

    /**
     * 监听帖子点赞的队列
     * @return
     */
    @Bean
    public Queue QuestionThumbQueue(){
        return new Queue(
                MqConstants.QUESTION_THUMB_QUEUE,
                true,
                false,
                false,
                null
        );
    }

    /**
     * 监听帖子评论的绑定
     * @return
     */
    @Bean
    public Binding QuestionCommentBinding(){
        return new Binding(
                MqConstants.QUESTION_COMMENT_QUEUE,
                Binding.DestinationType.QUEUE,
                MqConstants.QUESTION_EXCHANGE,
                MqConstants.QUESTION_COMMENT_ROUTINGKEY,
                null
        );
    }
    /**
     * 监听帖子点赞的绑定
     * @return
     */
    @Bean
    public Binding CommentThumbBinding(){
        return new Binding(
                MqConstants.QUESTION_THUMB_QUEUE,
                Binding.DestinationType.QUEUE,
                MqConstants.QUESTION_EXCHANGE,
                MqConstants.QUESTION_THUMB_ROUTINGKEY,
                null
        );
    }
}
