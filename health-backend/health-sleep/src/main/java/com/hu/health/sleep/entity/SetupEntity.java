package com.hu.health.sleep.entity;

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
 * @date 2022-02-27 15:22:21
 */
@Data
@TableName("sleep_setup")
public class SetupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long userId;
	/**
	 * 
	 */
	private Integer startShaking;
	/**
	 * 
	 */
	private Integer durationReminder;
	/**
	 * 
	 */
	private Integer bedtimeReminder;
	/**
	 * 
	 */
	private Integer recordReminder;

}
