package com.hu.health.sport.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hu.health.sport.entity.DataEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.health.sport.vo.SportDataDetailsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * 运动数据
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
@Mapper
public interface DataDao extends BaseMapper<DataEntity> {

    @Select("select sum(duration) from sport_data where user_id=#{uid} group by date=date(now())")
    Integer selectScheduleToday(@Param("uid") Long uid, @Param("date") Date date);


    IPage<SportDataDetailsVo> dataDetail(IPage<SportDataDetailsVo> dataDetailsVoIPage,
                                         @Param("uid")Long uid,
                                         @Param("day")int day);

    IPage<SportDataDetailsVo> dataDetailWeek(IPage<SportDataDetailsVo> dataDetailsVoIPage,
                                         @Param("uid")Long uid,
                                         @Param("week")int week);

    IPage<SportDataDetailsVo> dataDetailMonth(IPage<SportDataDetailsVo> dataDetailsVoIPage,
                                         @Param("uid")Long uid,
                                         @Param("month")int month);

    DataEntity hashSameTypeToday(DataEntity data);

    void updateDurations(DataEntity data);
}
