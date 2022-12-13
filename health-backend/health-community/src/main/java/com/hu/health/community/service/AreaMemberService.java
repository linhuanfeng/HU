package com.hu.health.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.community.entity.AreaMemberEntity;

import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-21 22:55:13
 */
public interface AreaMemberService extends IService<AreaMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils likeAreas(Map<String, Object> params,Long memberId);
}

