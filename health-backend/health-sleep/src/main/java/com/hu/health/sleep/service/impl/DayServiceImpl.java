package com.hu.health.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hu.health.sleep.dao.DayDao;
import com.hu.health.sleep.entity.DayEntity;
import com.hu.health.sleep.service.DayService;
import com.hu.health.sleep.util.getWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;


@Service("dayService")
public class DayServiceImpl extends ServiceImpl<DayDao, DayEntity> implements DayService {

    @Autowired
    DayDao dayDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DayEntity> page = this.page(
                new Query<DayEntity>().getPage(params),
                new QueryWrapper<DayEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void startSleep(Long user_id, String date) {
        UpdateWrapper<DayEntity> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("user_id",user_id).eq("date",date).set("start_time",new Date());
        dayDao.update(null,updateWrapper);
    }

    @Override
    public String duration(Long user_id, String date) {
        QueryWrapper<DayEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user_id).eq("date",date);
        DayEntity dayEntity = dayDao.selectOne(queryWrapper);
        Date startTime = dayEntity.getStartTime();
        long diff = new Date().getTime() - startTime.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        Double score;
        switch ((int) diffHours){
            case 5:score= Double.valueOf(60);break;
            case 6:score=Double.valueOf(70);break;
            case 7:score=Double.valueOf(80);break;
            case 8:score=Double.valueOf(90);break;
            case 9:score=Double.valueOf(100);break;
            default:score=Double.valueOf(40);

        }
        UpdateWrapper<DayEntity> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("user_id",user_id).eq("date",date).set("Duration",""+diffHours+"小时:"+diffMinutes+"分:"+diffSeconds+"秒").set("score",score);
        dayDao.update(null,updateWrapper);
//        long diffDays = diff / (24 * 60 * 60 * 1000);
         return ""+diffHours+"小时:"+diffMinutes+"分:"+diffSeconds+"秒";

    }

    @Override
    public void endSleep(Long user_id, String date) {
        UpdateWrapper<DayEntity> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("user_id",user_id).eq("date",date).set("end_time",new Date());
        dayDao.update(null,updateWrapper);
    }

    @Override
    public String allDuration(Long user_id, String date) {
        QueryWrapper<DayEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user_id).eq("date",date);
        DayEntity dayEntity = dayDao.selectOne(queryWrapper);
        long diff = dayEntity.getEndTime().getTime() - dayEntity.getStartTime().getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
//        long diffDays = diff / (24 * 60 * 60 * 1000);
        return ""+diffHours+"小时:"+diffMinutes+"分:"+diffSeconds+"秒";
    }

    @Override
    public List<DayEntity> getWeekList(Long id) {
       return dayDao.selectWeekList(id, getWeek.getBeginDayOfWeek(),getWeek.getEndDayOfWeek());
    }

    @Override
    public List<DayEntity> getMonthList(Long id) {
        return dayDao.selectMonthList(id,getWeek.getBeginDayOfMonth(),getWeek.getEndDayOfMonth());
    }

    @Override
    public Double getDayScore(Long user_id, String date) {
        QueryWrapper<DayEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user_id).eq("date",date);
        DayEntity dayEntity = dayDao.selectOne(queryWrapper);
        return dayEntity.getScore();
    }

    @Override
    public Double getWeekScore(Long user_id) {
        List<DayEntity> weekList = this.getWeekList(user_id);
        if(weekList.size()==0)return 0.0;
        Double sum= Double.valueOf(0);
        for (DayEntity day:weekList) {
            sum+=day.getScore();
        }
        return sum/weekList.size();
    }

    @Override
    public Double getMonthScore(Long user_id) {
        List<DayEntity> monthList = this.getMonthList(user_id);
        if(monthList.size()==0)return 0.0;
        Double sum= Double.valueOf(0);
        for (DayEntity day:monthList) {
            sum+=day.getScore();
        }
        return sum/monthList.size();
    }

}