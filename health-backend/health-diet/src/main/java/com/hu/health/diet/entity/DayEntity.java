package com.hu.health.diet.entity;

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
 * @date 2022-02-17 06:09:21
 */
@Data
@TableName("diet_day")
public class DayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 
	 */
	private String brekfast;
	/**
	 * 
	 */
	private String lunch;
	/**
	 * 
	 */
	private String dinner;
	/**
	 * 
	 */
	private String adds;
	/**
	 * 
	 */
	private String day;

}
