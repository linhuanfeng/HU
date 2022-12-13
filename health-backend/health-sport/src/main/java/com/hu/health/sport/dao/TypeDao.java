package com.hu.health.sport.dao;

import com.hu.health.sport.entity.TypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运动类型
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:31
 */
@Mapper
public interface TypeDao extends BaseMapper<TypeEntity> {
	
}
