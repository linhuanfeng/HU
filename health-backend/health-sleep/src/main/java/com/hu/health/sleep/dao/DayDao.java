package com.hu.health.sleep.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.health.sleep.entity.DayEntity;
import com.mysql.cj.conf.PropertyDefinitions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-27 15:22:21
 */
@Mapper
public interface DayDao extends BaseMapper<DayEntity> {

   List<DayEntity> selectWeekList(@Param("user_id") Long id, @Param("beginweek")Date beginweek,@Param("endweek")Date endweek);
   List<DayEntity> selectMonthList(@Param("user_id") Long id, @Param("beginweek")Date beginweek,@Param("endweek")Date endweek);

}
