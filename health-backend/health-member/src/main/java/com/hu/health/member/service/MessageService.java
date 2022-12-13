package com.hu.health.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.pojo.community.MessageEntity;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.member.entity.MemberEntity;
import com.hu.health.member.to.MemberEntityTo;

import java.util.Map;

/**
 * 点赞评论消息
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-18 12:47:03
 */
public interface MessageService extends IService<MessageEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

