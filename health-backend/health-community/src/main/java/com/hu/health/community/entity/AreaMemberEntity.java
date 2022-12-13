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
 * @date 2022-02-21 22:55:13
 */
@Data
@TableName("community_area_member")
public class AreaMemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 会员id
	 */
	private Long memberId;
	/**
	 * 站点id
	 */
	private Long areaId;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
