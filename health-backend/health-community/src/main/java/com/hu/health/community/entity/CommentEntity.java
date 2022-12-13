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
 * @date 2022-02-20 20:53:35
 */
@Data
@TableName("community_comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 直接父评论id（也会就是回复那一条评论的id），0表示一级评论
	 */
	private Long parentId;
	/**
	 * 祖父评论id,即对应一级评论的id,方便查询一级评论下的所有评论
	 */
	private Long grandParentId;
	/**
	 * 帖子编号
	 */
	private Long questionId;
	/**
	 * 评论员id
	 */
	private Long creatorId;
	/**
	 * 评论员昵称
	 */
	private String creatorName;
	/**
	 * 创建时间
	 */
	private Date createTime=new Date();
	/**
	 * 点赞数
	 */
	private Long likeCount=0l;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 评论数
	 */
	private Integer commentCount=0;
	/**
	 * 逻辑删除，折叠等等
	 * 1表示逻辑删除
	 */
//	@TableField(exist =false)
	private Integer status=0;
}
