package com.hu.health.sport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.sport.entity.MusicEntity;

import java.util.Map;

/**
 * 运动音乐
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
public interface MusicService extends IService<MusicEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

