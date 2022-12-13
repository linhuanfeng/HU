package com.hu.health.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.community.entity.ThumbsEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 22:10:15
 */
public interface ThumbsService extends IService<ThumbsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveRedis(ThumbsEntity thumbs);

    void removeRedis(ThumbsEntity thumb);

    boolean saveEntity(ThumbsEntity entity);
    boolean saveEntityRedis(ThumbsEntity entity);
    boolean removeEntity(ThumbsEntity entity);

    void removeEntitiesByQuestionIds(List<Long> asList);
}

