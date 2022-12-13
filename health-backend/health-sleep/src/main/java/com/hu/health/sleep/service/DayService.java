package com.hu.health.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.sleep.entity.DayEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-27 15:22:21
 */
public interface DayService extends IService<DayEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //开始睡眠
    void startSleep(Long user_id,String date);

    //获取持续睡眠时间
    String duration(Long user_id, String date);

    //结束睡眠
    void endSleep(Long user_id,String date);

    //获取当日总睡眠时长(结束后)
    String allDuration(Long user_id,String date);


    //获取本周睡眠数据
    List<DayEntity> getWeekList(Long id);

    //获取本月睡眠数据
    List<DayEntity> getMonthList(Long id);

    //获取当日睡眠星际
    Double getDayScore(Long user_id,String date);

    //获取本周睡眠平均分
    Double getWeekScore(Long user_id);

    //获取本月睡眠平均分
    Double getMonthScore(Long user_id);

}

