package com.hu.health.consult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.common.utils.Query;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.consult.dao.AskDao;
import com.hu.health.consult.entity.AskEntity;
import com.hu.health.consult.service.AskService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AskServiceImpl extends ServiceImpl<AskDao, AskEntity> implements AskService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AskEntity> page = this.page(
                new Query<AskEntity>().getPage(params),
                new QueryWrapper<AskEntity>()
        );

        return new PageUtils(page);
    }
}
