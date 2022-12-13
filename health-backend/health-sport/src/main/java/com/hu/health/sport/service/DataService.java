package com.hu.health.sport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.sport.entity.DataEntity;
import com.hu.health.sport.vo.ScheduleVo;

import java.util.Map;

/**
 * 运动数据
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
public interface DataService extends IService<DataEntity> {

    PageUtils queryPage(Map<String, Object> params);

    ScheduleVo scheduleToday(Long uid);

    PageUtils detail(Map<String, Object> params, Long uid, Integer day);

    PageUtils detailWeek(Map<String, Object> params, Long uid, Integer week);

    PageUtils detailMonth(Map<String, Object> params, Long uid, Integer month);

    void insert(DataEntity data);
}

