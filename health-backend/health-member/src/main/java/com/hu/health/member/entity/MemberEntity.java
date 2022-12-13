package com.hu.health.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-18 12:47:03
 */
@Data
@TableName("ums_member")
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 会员等级id
	 */
	private Long levelId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 生日
	 */
	private Date birth;
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 职业
	 */
	private String job;
	/**
	 * 个性签名
	 */
	private String sign;
	/**
	 * 积分
	 */
	private Integer integration;
	/**
	 * 成长值
	 */
	private Integer growth;
	/**
	 * 启用状态
	 */
	private Integer status;
	/**
	 * 注册时间
	 */
	private Date createTime;
	/**
	 * 用户头像图片
	 */
	private String avatar;
	/**
	 * 微信登录openid
	 */
	private String openId;
	/**
	 * 微信登录会话KEY
	 */
	private String sessionKey;

	private String province;

	private String expect_id;

	@TableField(exist = false)
	private String token;

	/**
	 * 身高
	 */
	private Integer height;
	/**
	 * 体重
	 */
	private Integer weight;

	@TableField(exist = false)
	private Double BMI;

	private Long likeCount;
	private Long commentCount;
	private Long questionCount;
}
