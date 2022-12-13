package com.hu.health.community.service.impl;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.common.utils.Query;
/*sada*/
import com.hu.health.community.dao.AreaDao;
import com.hu.health.community.entity.AreaEntity;
import com.hu.health.community.service.AreaService;


@Service("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaDao, AreaEntity> implements AreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AreaEntity> page = this.page(
                new Query<AreaEntity>().getPage(params),
                new QueryWrapper<AreaEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void follow(Long id) {
        this.baseMapper.follow(id);
    }

    @Override
    public void unFollow(Long id) {
        this.baseMapper.unFollow(id);
    }

}