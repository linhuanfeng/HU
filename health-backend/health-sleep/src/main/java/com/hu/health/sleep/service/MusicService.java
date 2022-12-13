package com.hu.health.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.sleep.entity.MusicEntity;

import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-27 15:22:20
 */
public interface MusicService extends IService<MusicEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

