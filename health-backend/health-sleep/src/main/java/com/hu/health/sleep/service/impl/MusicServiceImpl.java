package com.hu.health.sleep.service.impl;

import com.hu.health.sleep.dao.MusicDao;
import com.hu.health.sleep.entity.MusicEntity;
import com.hu.health.sleep.service.MusicService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;



@Service("musicService")
public class MusicServiceImpl extends ServiceImpl<MusicDao, MusicEntity> implements MusicService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MusicEntity> page = this.page(
                new Query<MusicEntity>().getPage(params),
                new QueryWrapper<MusicEntity>()
        );

        return new PageUtils(page);
    }

}