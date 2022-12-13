package com.hu.health.sport.dao;

import com.hu.health.sport.entity.PlanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运动计划
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
@Mapper
public interface PlanDao extends BaseMapper<PlanEntity> {
	
}
