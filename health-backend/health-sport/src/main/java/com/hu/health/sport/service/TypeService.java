package com.hu.health.sport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.sport.entity.TypeEntity;

import java.util.Map;

/**
 * 运动类型
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:31
 */
public interface TypeService extends IService<TypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

