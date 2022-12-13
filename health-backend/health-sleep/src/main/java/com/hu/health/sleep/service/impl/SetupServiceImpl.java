package com.hu.health.sleep.service.impl;

import com.hu.health.sleep.dao.SetupDao;
import com.hu.health.sleep.entity.SetupEntity;
import com.hu.health.sleep.service.SetupService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;



@Service("setupService")
public class SetupServiceImpl extends ServiceImpl<SetupDao, SetupEntity> implements SetupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SetupEntity> page = this.page(
                new Query<SetupEntity>().getPage(params),
                new QueryWrapper<SetupEntity>()
        );

        return new PageUtils(page);
    }

}