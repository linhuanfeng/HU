package com.hu.health.member.service.impl;

import com.hu.health.member.entity.ExpertEntity;
import com.hu.health.member.dao.ExpertDao;
import com.hu.health.member.service.ExpertService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;



@Service("umsExpertService")
public class ExpertServiceImpl extends ServiceImpl<ExpertDao, ExpertEntity> implements ExpertService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ExpertEntity> page = this.page(
                new Query<ExpertEntity>().getPage(params),
                new QueryWrapper<ExpertEntity>()
        );

        return new PageUtils(page);
    }

}