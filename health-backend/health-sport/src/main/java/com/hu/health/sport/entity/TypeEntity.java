package com.hu.health.sport.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 运动类型
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:31
 */
@Data
@TableName("sport_type")
public class TypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 运动类型编号
	 */
	@TableId
	private Long id;
	/**
	 * 类型名字
	 */
	private String name;
	/**
	 * 每分钟消耗多少卡路里
	 */
	private Integer caloriePerMinute;
	/**
	 * 温馨提示
	 */
	private String tip;

	private String level;

	private String smallImg;
	private String bigImg;
}
