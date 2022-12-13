package com.hu.health.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.health.common.pojo.community.MessageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:35
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {
}
