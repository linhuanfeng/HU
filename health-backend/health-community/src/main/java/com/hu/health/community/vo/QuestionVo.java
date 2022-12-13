package com.hu.health.community.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QuestionVo implements Serializable {
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date modifiedTime;
    /**
     * 创建者id
     */
    private Long creator;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 浏览数
     */
    private Integer viewCount;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 标签
     */
    private String tag;
    /**
     * 是否置顶
     */
    private Integer sticky;
    /**
     * 站点id
     */
    private Long areaId;

    private String areaName;

    private String creatorImg;

    private String img;
}
