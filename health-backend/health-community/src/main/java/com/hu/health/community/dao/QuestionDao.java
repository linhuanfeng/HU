package com.hu.health.community.dao;

import com.hu.health.common.pojo.community.QuestionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:35
 */
@Mapper
public interface QuestionDao extends BaseMapper<QuestionEntity> {

    /**
     * 帖子点赞加n
     * @param id
     * @param delta
     */
    @Update("update community_question set like_count=like_count+#{delta} where id=#{id}")
    void thumb(@Param("id") Long id,@Param("delta") int delta);

    /**
     * 帖子点赞减n
     * @param id
     * @param delta
     */
    @Update("update community_question set like_count=like_count-#{delta} where id=#{id}")
    void unThumb(@Param("id")Long id,@Param("delta") int delta);

    /**
     * 帖子浏览数加1
     * @param id
     */
    @Update("update community_question set view_count=view_count+1 where id=#{id}")
    void view(Long id);

    /**
     * 帖子评论数加n
     * @param id
     * @param delta
     */
    @Update("update community_question set comment_count=comment_count+#{delta} where id=#{id}")
    void comment(@Param("id") Long id,@Param("delta") int delta);

    /**
     * 帖子评论数减n
     * @param id
     * @param delta
     */
    @Update("update community_question set comment_count=comment_count-#{delta} where id=#{id}")
    void unComment(@Param("id") Long id,@Param("delta") int delta);
}
