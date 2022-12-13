package com.hu.health.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.member.dao.EvaluationResDao;
import com.hu.health.member.service.EvaluationResService;
import com.hu.health.member.entity.EvaluationResEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class EvaluationResServiceImpl extends ServiceImpl<EvaluationResDao, EvaluationResEntity> implements EvaluationResService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EvaluationResEntity> page = this.page(
                new Query<EvaluationResEntity>().getPage(params),
                new QueryWrapper<EvaluationResEntity>()
        );

        return new PageUtils(page);
    }

}