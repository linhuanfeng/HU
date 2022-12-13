package com.hu.health.sport.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 运动计划
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
@Data
@TableName("sport_plan")
public class PlanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 计划编号
	 */
	@TableId
	private Long id;
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 每日计划时长
	 */
	private Integer planPerDay;
	/**
	 * 每周计划时长
	 */
	private Integer planPerWeek;

}
