package com.hu.health.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:36
 */
@Data
@TableName("community_area")
public class AreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 站点id
	 */
	@TableId
	private Long id;
	/**
	 * 名字
	 */
	private String title;
	/**
	 * 图片
	 */
	private String img;
	/**
	 * 关注数
	 */
	private String fanCount;
	/**
	 * 创建者id
	 */
	private Long creator;
	/**
	 * 发帖数
	 */
	private Integer questionCount;
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
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date modifiedTime;

}
