package com.hu.health.sport.service.impl;

import com.hu.health.sport.entity.PlanEntity;
import com.hu.health.sport.service.PlanService;
import com.hu.health.sport.vo.ScheduleVo;
import com.hu.health.sport.vo.SportDataDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;

import com.hu.health.sport.dao.DataDao;
import com.hu.health.sport.entity.DataEntity;
import com.hu.health.sport.service.DataService;
import org.springframework.util.Assert;


@Service("dataService")
public class DataServiceImpl extends ServiceImpl<DataDao, DataEntity> implements DataService {

    @Autowired
    PlanService planService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataEntity> page = this.page(
                new Query<DataEntity>().getPage(params),
                new QueryWrapper<DataEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查出今日健身时长和计划时长
     * @param uid
     * @return
     */
    @Override
    public ScheduleVo scheduleToday(Long uid) {
        // 今日已完成时长
        Integer scheduleToday = baseMapper.selectScheduleToday(uid, new Date());
        // 获取今日运动计划
        PlanEntity planEntity = planService.getById(uid);
        return generateScheduleVo(uid,scheduleToday,planEntity);
    }

    private ScheduleVo generateScheduleVo(Long uid, Integer scheduleToday, PlanEntity planEntity) {
        Assert.notNull(planEntity,"您还没设置运动计划");
        scheduleToday=scheduleToday==null?0:scheduleToday;
        Integer planPerDay = planEntity.getPlanPerDay(); // 今日计划
        int unFinish=planPerDay>=scheduleToday?planPerDay-scheduleToday:0; // 未完成
        return new ScheduleVo(uid,planPerDay,scheduleToday,unFinish);
    }


    @Override
    public PageUtils detail(Map<String, Object> params, Long uid, Integer day) {
        if(day==null){
            day=new Integer(0);
        }
        IPage<SportDataDetailsVo> dataDetailsVo=baseMapper.dataDetail(new Query<SportDataDetailsVo>().getPage(params),uid,day);
        return new PageUtils(dataDetailsVo);
    }

    @Override
    public PageUtils detailWeek(Map<String, Object> params, Long uid, Integer week) {
        if(week==null){
            week=new Integer(0);
        }
        IPage<SportDataDetailsVo> dataDetailsVo=baseMapper.dataDetailWeek(new Query<SportDataDetailsVo>().getPage(params),uid,week);
        return new PageUtils(dataDetailsVo);
    }

    @Override
    public PageUtils detailMonth(Map<String, Object> params, Long uid, Integer month) {
        if(month==null){
            month=new Integer(0);
        }
        IPage<SportDataDetailsVo> dataDetailsVo=baseMapper.dataDetailMonth(new Query<SportDataDetailsVo>().getPage(params),uid,month);
        return new PageUtils(dataDetailsVo);
    }

    @Override
    public void insert(DataEntity data) {
        // 当前是否由相同类型得运动，有则更新，否则新增
        DataEntity entity=this.baseMapper.hashSameTypeToday(data);
        if(entity==null){
            this.baseMapper.insert(data);
        }else {
            // data默认id为null
            data.setId(entity.getId());
            baseMapper.updateDurations(data);
        }
    }

}