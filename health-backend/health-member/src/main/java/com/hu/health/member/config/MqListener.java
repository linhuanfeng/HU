package com.hu.health.member.config;

import com.hu.health.common.MqConstants;
import com.hu.health.common.pojo.community.MessageEntity;
import com.hu.health.member.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MqListener {


    @Resource
    private MessageService messageService;

    /**
     * 帖子评论信息
     * @param message
     */
    @RabbitListener(queues = MqConstants.QUESTION_COMMENT_QUEUE)
    public void commentQuestion(MessageEntity message) {
        boolean save = messageService.save(message);
        if(save){
            log.info("帖子评论成功{}",message);
        }else {
            log.info("帖子评论失败{}",message);
        }
    }

    /**
     * 帖子信息
     * @param message
     */
    @RabbitListener(queues = MqConstants.QUESTION_THUMB_QUEUE)
    public void thumbQuestion(MessageEntity message){
        boolean save = messageService.save(message);
        if(save){
            log.info("帖子成功{}",message);
        }else {
            log.info("帖子失败{}",message);
        }
    }
}
