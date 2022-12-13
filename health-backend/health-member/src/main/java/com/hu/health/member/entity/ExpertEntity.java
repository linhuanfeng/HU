package com.hu.health.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-22 21:18:14
 */
@Data
@TableName("ums_expert")
public class ExpertEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	private Long id;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 职业
	 */
	private String identity;
	/**
	 * 领域
	 */
	private String field;
	/**
	 * 大学
	 */
	private String university;
	/**
	 * 简介
	 */
	private String introduction;
	/**
	 * 擅长标签
	 */
	private String tag;
	/**
	 * 星级
	 */
	private BigDecimal score;
	/**
	 * 价格
	 */
	private BigDecimal fare;
	/**
	 * 线上线下
	 */
	private String type;
	/**
	 * 证明材料
	 */
	private String materials;

}
