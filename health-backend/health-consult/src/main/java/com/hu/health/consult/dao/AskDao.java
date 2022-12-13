package com.hu.health.consult.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.health.consult.entity.AskEntity;
import com.hu.health.consult.entity.HeadEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AskDao extends BaseMapper<AskEntity> {
}
