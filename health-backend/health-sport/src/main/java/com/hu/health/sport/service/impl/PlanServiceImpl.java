package com.hu.health.sport.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;

import com.hu.health.sport.dao.PlanDao;
import com.hu.health.sport.entity.PlanEntity;
import com.hu.health.sport.service.PlanService;


@Service("planService")
public class PlanServiceImpl extends ServiceImpl<PlanDao, PlanEntity> implements PlanService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PlanEntity> page = this.page(
                new Query<PlanEntity>().getPage(params),
                new QueryWrapper<PlanEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void updateByUId(PlanEntity plan) {
        if(plan.getUserId()==null){
            throw new RuntimeException("用户id不能为空");
        }
        this.baseMapper.update(plan,new QueryWrapper<PlanEntity>().eq("user_id",plan.getUserId()));
    }

}