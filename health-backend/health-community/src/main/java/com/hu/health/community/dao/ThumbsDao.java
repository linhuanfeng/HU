package com.hu.health.community.dao;

import com.hu.health.community.entity.ThumbsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 22:10:15
 */
@Mapper
public interface ThumbsDao extends BaseMapper<ThumbsEntity> {
    // 根据帖子id批量删除点赞记录
    void removeThumbsByQuestionIds(List<Long> questionIds);
}
