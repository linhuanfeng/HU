package com.hu.health.selfdiagonsis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-19 00:28:15
 */
@Data
@TableName("selfdiagonsis_symptom")
public class SelfdiagonsisSymptomEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long catId;
	/**
	 * 
	 */
	private Long parentCid;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer catLevel;
	/**
	 * 
	 */
//	@TableLogic
	private Integer showStatus;
	/**
	 * 
	 */
	private Integer sort;
	/**
	 * 
	 */
	private String icon;
	/**
	 * 
	 */
	private String relatedIntroduction;
	/**
	 * 
	 */
	private String symptom;
	/**
	 * 
	 */
	private String preventiveMethods;

@TableField(exist = false)
	private List<SelfdiagonsisSymptomEntity> children;

}
