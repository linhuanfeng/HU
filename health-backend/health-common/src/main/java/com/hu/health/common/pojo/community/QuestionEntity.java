package com.hu.health.common.pojo.community;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:35
 */
@Data
@TableName("community_question")
public class QuestionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */

	@TableId(value = "id",type = IdType.AUTO)
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
	private Long creatorId;
	/**
	 * 创建者昵称
	 */
	private String creatorName;
	/**
	 * 评论数
	 */
	private Integer commentCount=0;
	/**
	 * 浏览数
	 */
	private Integer viewCount=0;
	/**
	 * 点赞数
	 */
	private Integer likeCount=0;
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

	private String img;

	@TableField(exist = false)
	private List<String> suggestion;

}
