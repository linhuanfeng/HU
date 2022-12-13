package com.hu.health.community.service.impl;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.community.entity.AreaEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.common.utils.Query;

import com.hu.health.community.dao.AreaMemberDao;
import com.hu.health.community.entity.AreaMemberEntity;
import com.hu.health.community.service.AreaMemberService;


@Service("areaMemberService")
public class AreaMemberServiceImpl extends ServiceImpl<AreaMemberDao, AreaMemberEntity> implements AreaMemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AreaMemberEntity> page = this.page(
                new Query<AreaMemberEntity>().getPage(params),
                new QueryWrapper<AreaMemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils likeAreas(Map<String, Object> params,Long memberId) {
        IPage<AreaEntity> likeAreas = this.baseMapper.selectLikeAreas(new Query<AreaEntity>().getPage(params), memberId);
        return new PageUtils(likeAreas);
    }

}