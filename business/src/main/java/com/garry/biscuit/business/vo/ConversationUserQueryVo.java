package com.garry.biscuit.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConversationUserQueryVo extends ConversationQueryVo {

    /**
     * 最后一条消息
     */
    private String lastMessage;

    /**
     * 最后一条消息时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastMessageTime;

    /**
     * 聊天对象名称
     */
    private String chatterName;

    /**
     * 聊天对象头像
     */
    private String chatterAvatar;

    /**
     * 未读消息数量
     */
    private Integer unreadCount;

}
