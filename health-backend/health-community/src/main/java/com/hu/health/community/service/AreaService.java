package com.hu.health.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.community.entity.AreaEntity;

import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:36
 */
public interface AreaService extends IService<AreaEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void follow(Long id);

    void unFollow(Long id);
}

