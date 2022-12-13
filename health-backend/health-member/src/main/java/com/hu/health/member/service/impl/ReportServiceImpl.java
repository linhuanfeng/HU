package com.hu.health.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.member.entity.ReportEntity;
import com.hu.health.member.dao.ReportDao;
import com.hu.health.member.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class ReportServiceImpl extends ServiceImpl<ReportDao, ReportEntity> implements ReportService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReportEntity> page = this.page(
                new Query<ReportEntity>().getPage(params),
                new QueryWrapper<ReportEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public ReportEntity result(Integer score) {
        if(score>=70)score=1;
        else if(score>40) score=2;
        else score=3;
        ReportEntity reportEntity = baseMapper.selectOne(new QueryWrapper<ReportEntity>().eq("score", score));
        return reportEntity;
    }

}