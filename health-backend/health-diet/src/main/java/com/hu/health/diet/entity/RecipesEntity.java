package com.hu.health.diet.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @date 2022-02-17 06:09:21
 */
@Data
@TableName("diet_recipes")
public class RecipesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
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
	private Integer shouStatus;
	/**
	 * 
	 */
	private String icon;
	/**
	 * 
	 */
	private Integer sort;
	/**
	 * 
	 */
	private String quantityOfHeat;
	/**
	 *
	 */
	private String carbonWaterRatio;
	/**
	 *
	 */
	private String proportionOfFat;
	/**
	 *
	 */
	private String proportionOfProtein;

	@TableField(exist = false)
	private List<RecipesEntity> children;

}
