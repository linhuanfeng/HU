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
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-02-14 20:50:23
 */
@Data
@TableName("diet_menu")
public class DietMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DietMenuEntity(){

	}
	/**
	 * 分类唯一di
	 */
	@TableId
	private Long catId;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 父分类
	 */
	private Long parentCid;
	/**
	 * 层级
	 */
	private Integer catLevel;
	/**
	 * 是否显示
	 */
	private Integer showStatus;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 图标地址
	 */
	private String icon;
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
	private List<DietMenuEntity> children;

//
//	public Long getCatId() {
//		return catId;
//	}
//
//	public void setCatId(Long catId) {
//		this.catId = catId;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Long getParentCid() {
//		return parentCid;
//	}
//
//	public void setParentCid(Long parentCid) {
//		this.parentCid = parentCid;
//	}
//
//	public Integer getCatLevel() {
//		return catLevel;
//	}
//
//	public void setCatLevel(Integer catLevel) {
//		this.catLevel = catLevel;
//	}
//
//	public Integer getShowStatus() {
//		return showStatus;
//	}
//
//	public void setShowStatus(Integer showStatus) {
//		this.showStatus = showStatus;
//	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}
//
//	public String getIcon() {
//		return icon;
//	}
//
//	public void setIcon(String icon) {
//		this.icon = icon;
//	}
//
//	public String getQuantityOfHeat() {
//		return quantityOfHeat;
//	}
//
//	public void setQuantityOfHeat(String quantityOfHeat) {
//		this.quantityOfHeat = quantityOfHeat;
//	}
//
//	public String getCarbonWaterRatio() {
//		return carbonWaterRatio;
//	}
//
//	public void setCarbonWaterRatio(String carbonWaterRatio) {
//		this.carbonWaterRatio = carbonWaterRatio;
//	}
//
//	public String getProportionOfFat() {
//		return proportionOfFat;
//	}
//
//	public void setProportionOfFat(String proportionOfFat) {
//		this.proportionOfFat = proportionOfFat;
//	}
//
//	public String getProportionOfProtein() {
//		return proportionOfProtein;
//	}
//
//	public void setProportionOfProtein(String proportionOfProtein) {
//		this.proportionOfProtein = proportionOfProtein;
//	}
//
//	public List<DietMenuEntity> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<DietMenuEntity> children) {
//		this.children = children;
//	}
//
//	@Override
//	public String toString() {
//		return "DietMenuEntity{" +
//				"catId=" + catId +
//				", name='" + name + '\'' +
//				", parentCid=" + parentCid +
//				", catLevel=" + catLevel +
//				", showStatus=" + showStatus +
//				", sort=" + sort +
//				", icon='" + icon + '\'' +
//				", quantityOfHeat='" + quantityOfHeat + '\'' +
//				", carbonWaterRatio='" + carbonWaterRatio + '\'' +
//				", proportionOfFat='" + proportionOfFat + '\'' +
//				", proportionOfProtein='" + proportionOfProtein + '\'' +
//				", children=" + children +
//				'}';
//	}

	@Override
	public boolean equals(Object o) {
	  return this==o;
	}
}
