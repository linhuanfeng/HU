package com.hu.health.sport.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 运动数据
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
@Data
@TableName("sport_data")
public class DataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 运动日期
	 */
	private Date date=new Date();
	/**
	 * 运动类型
	 */
	private Long type;
	/**
	 * 运动时长
	 */
	private Integer duration;

	/**
	 * 用户id
	 */
	private Long userId;

}
