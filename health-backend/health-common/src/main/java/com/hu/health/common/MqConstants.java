package com.hu.health.common;

public interface MqConstants {
        /**
         * 监听帖子的交换机
         */
        String QUESTION_EXCHANGE="question-exchange";
        /**
         * 监听帖子更新的队列
         */
        String QUESTION_UPDATE_QUEUE="question-update-queue";
        /**
         * 监听帖子删除的队列
         */
        String QUESTION_DELETE_QUEUE="question-delete-queue";
        /**
         * 监听评论事件的队列
         */
        String QUESTION_COMMENT_QUEUE="question-comment-queue";
        /**
         * 监听点赞事件的队列
         */
        String QUESTION_THUMB_QUEUE="question-thumb-queue";
        /**
         * 监听帖子更新的路由键
         */
        String QUESTION_UPDATE_ROUTINGKEY="question-update-routingKey";
        /**
         * 监听帖子删除的路由键
         */
        String QUESTION_DELETE_ROUTINGKEY="question-delete-routingKey";
        /**
         * 监听帖子评论的路由键
         */
        String QUESTION_COMMENT_ROUTINGKEY="question-comment-routingKey";
        /**
         * 监听帖子点赞的路由键
         */
        String QUESTION_THUMB_ROUTINGKEY="question-thumb-routingKey";
}
