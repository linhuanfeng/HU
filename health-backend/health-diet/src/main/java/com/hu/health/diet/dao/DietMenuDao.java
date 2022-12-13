package com.hu.health.diet.dao;

import com.hu.health.diet.entity.DietMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-02-14 20:50:23
 */
@Mapper
public interface DietMenuDao extends BaseMapper<DietMenuEntity> {
    DietMenuEntity showbreakfast(Long id);
    DietMenuEntity showlunch(Long id);
    DietMenuEntity showdinner(Long id);
    DietMenuEntity showadds(Long id);
}
