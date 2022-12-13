package com.hu.health.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.pojo.community.QuestionEntity;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.community.entity.CommentEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:35
 */
public interface QuestionService extends IService<QuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void thumbs(Long id,Long number);

    void thumb(Long id);

    void unThumb(Long questionId);

    void comment(Long id);

    void unComment(Long id);

    void thumb(Long id,int delta);

    void unThumb(Long questionId,int delta);

    void comment(Long id,int delta);

    void unComment(Long id,int delta);

    void view(Long id);

    void myRemoveByIds(List<Long> asList);

    void mySave(QuestionEntity question);
}

