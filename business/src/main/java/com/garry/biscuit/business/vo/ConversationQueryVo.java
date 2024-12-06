package com.garry.biscuit.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class ConversationQueryVo {

    /**
     * id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 卖家id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sellerId;

    /**
     * 买家id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long buyerId;

    /**
     * 商品id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long productId;

    /**
     * 商品图片
     */
    private String productPicture;

    /**
     * 新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    // ----------------------------------- user  -----------------------------------

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
