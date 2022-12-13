package com.hu.health.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.pojo.community.MessageEntity;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.member.dao.MessageDao;
import com.hu.health.member.entity.ReportEntity;
import com.hu.health.member.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object targetId = params.get("targetId");
        Object status = params.get("status");
        IPage<MessageEntity> page = this.page(
                new Query<MessageEntity>().getPage(params),
                new QueryWrapper<MessageEntity>()
                        .eq(targetId!=null&& StringUtils.hasLength((String) targetId),"target_id",targetId)
                        .eq("status",status));
        return new PageUtils(page);
    }
}