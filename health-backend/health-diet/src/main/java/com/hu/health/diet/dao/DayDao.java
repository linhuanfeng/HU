package com.hu.health.diet.dao;

import com.hu.health.diet.entity.DayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.health.diet.entity.DietMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-17 06:09:21
 */
@Mapper
public interface DayDao extends BaseMapper<DayEntity> {


 void updateBreakfast(@Param("user_id") Long user_id, @Param("date") String date, @Param("breakfast") String breakfast);

 void updateLunch(@Param("user_id") Long user_id, @Param("date") String date, @Param("lunch") String breakfast);

 void updateDinner(@Param("user_id") Long user_id, @Param("date") String date, @Param("dinner") String breakfast);

 void updateAdds(@Param("user_id") Long user_id, @Param("date") String date, @Param("adds") String breakfast);

//
}
