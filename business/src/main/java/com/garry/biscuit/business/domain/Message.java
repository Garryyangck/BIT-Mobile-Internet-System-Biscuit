package com.garry.biscuit.business.domain;

import lombok.Data;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class Message {

    /**
     * id
     */
    private Long id;

    /**
     * 发出者id
     */
    private Long fromId;

    /**
     * 接收者id
     */
    private Long toId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 内容
     */
    private String content;

    /**
     * 消息类型|枚举[MessageTypeEnum]
     */
    private Integer type;

    /**
     * 是否已读，0-未读，1-已读
     */
    private Integer isRead;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}