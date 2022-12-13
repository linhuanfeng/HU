package com.hu.health.common.pojo.community;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 站内通知消息
 * 当用户点赞，评论，提醒用户消息
 */
@ToString
@Data
@TableName("ums_message")
public class MessageEntity implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;
    private Long fromId;
    private Long targetId;
    private Long questionId;
    private String questionTitle;
    private String content;
    /**
     * 状态转移：1未读->2已读->3已删除
     */
    private int status=1;
    private Date createTime=new Date();
}
