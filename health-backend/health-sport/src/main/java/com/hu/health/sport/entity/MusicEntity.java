package com.hu.health.sport.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 运动音乐
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
@Data
@TableName("sport_music")
public class MusicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 音乐编号
	 */
	@TableId
	private Long id;
	/**
	 * 音乐名称
	 */
	private String name;
	/**
	 * 音乐作者
	 */
	private Integer author;

}
