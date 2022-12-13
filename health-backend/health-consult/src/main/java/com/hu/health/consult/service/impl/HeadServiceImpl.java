package com.hu.health.consult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.common.utils.Query;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.consult.dao.HeadDao;
import com.hu.health.consult.entity.HeadEntity;
import com.hu.health.consult.service.HeadService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HeadServiceImpl extends ServiceImpl<HeadDao, HeadEntity> implements HeadService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HeadEntity> page = this.page(
                new Query<HeadEntity>().getPage(params),
                new QueryWrapper<HeadEntity>()
        );

        return new PageUtils(page);
    }
}
