package com.hu.health.community.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hu.health.community.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:35
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
    /**
     * 根据帖子id列表，删除帖子对应所有评论记录
     * @param questionIds
     */
    void removeCommentsByQuestionIds(List<Long> questionIds);

    /**
     * 所有一级评论
     *
     * 找到帖子id和父评论id对应的所有有效一级评论列表，排序规则：点赞人数、评论人数和创建时间降序
     * 查找一级评论：parentId=0
     * 查找某一评论k的直接子类：parentId=k
     * @param page
     * @param questionId 帖子id
     * @param parentId 父亲评论id
     * @return
     */
    @Select("select * " +
            "from community_comment " +
            "where question_id=#{questionId} and parent_id=#{parentId} and status=0 " +
            "order by like_count desc ,comment_count desc ,create_time desc ")
    IPage<CommentEntity> listByQidPid(IPage<CommentEntity> page,@Param("questionId") Long questionId,
            @Param("parentId") Long parentId);

    /**
     * 所有二级评论
     * 找到帖子id和一级评论id对应的所有有效二级评论，排序规则：点赞人数、评论人数和创建时间降序
     *
     * 查找一级评论的所有子类：grandParentId=一级评论id
     * @param page
     * @param questionId 帖子id
     * @param grandParentId 一级评论id
     * @return
     */
    @Select("select * " +
            "from community_comment " +
            "where question_id=#{questionId} and grand_parent_id=#{grandParentId} and status=0 " +
            "order by like_count desc ,comment_count desc ,create_time desc ")
    IPage<CommentEntity> listSecByGrandId(IPage<CommentEntity> page,
                                      @Param("questionId") Long questionId,
                                      @Param("grandParentId") Long grandParentId);

    /**
     * 评论的点赞数加1
     * @param id
     */
    @Update("update community_comment set like_count=like_count+1 where id=#{id}")
    void thumb(Long id);

    /**
     * 评论的点赞数减1
     * @param id
     */
    @Update("update community_comment set like_count=like_count-1 where id=#{id}")
    void unThumb(Long id);

    /**
     * 评论的评论数加1
     * @param id
     */
    @Update("update community_comment set comment_count=comment_count+1 where id=#{id}")
    void comment(Long id);

    /**
     * 评论的评论数减1
     * @param id
     */
    @Update("update community_comment set comment_count=comment_count-1 where id=#{id}")
    void uncomment(Long id);
}
