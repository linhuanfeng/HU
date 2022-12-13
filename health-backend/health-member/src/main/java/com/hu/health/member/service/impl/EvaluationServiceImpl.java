package com.hu.health.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.member.dao.EvaluationDao;
import com.hu.health.member.entity.EvaluationEntity;
import com.hu.health.member.service.EvaluationService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationDao, EvaluationEntity> implements EvaluationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EvaluationEntity> page = this.page(
                new Query<EvaluationEntity>().getPage(params),
                new QueryWrapper<EvaluationEntity>()
        );

        return new PageUtils(page);
    }

}