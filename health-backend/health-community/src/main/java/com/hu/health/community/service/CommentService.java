package com.hu.health.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.community.entity.CommentEntity;
import com.hu.health.community.entity.ThumbsEntity;
import com.hu.health.community.to.CommentTo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:35
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params,Long questionId,Long parent_id);

    /**
     * redis list中获取最新的评论
     *
     * 所有一级评论
     *
     * @param params
     * @param questionId
     * @param parent_id
     * @return
     */
//    @Deprecated
    PageUtils queryPageRedisList(Map<String, Object> params,Long questionId,Long parent_id);

    /**
     * 所有二级评论
     * @param params
     * @param questionId
     * @param grand_parent_id
     * @return
     */
    PageUtils listSecByGrandId(Map<String, Object> params,Long questionId,Long grand_parent_id);

    PageUtils queryPageHotAndLatest(Map<String, Object> params,Long questionId);
//    PageUtils queryPageList(Map<String, Object> params,Long qid);

    void saveEntity(CommentEntity entity);

    void delEntity(CommentTo to);

    void removeCommentsByQuestionIds(List<Long> questionIds);

    void thumb(Long id);

    void unThumb(Long id);
    void comment(Long id);
    void unComment(Long id);
}

