package com.garry.biscuit.business.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class MessageQueryVo {

    /**
     * id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 发出者id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long fromId;

    /**
     * 接收者id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long toId;

    /**
     * 商品id
     */
    @JsonSerialize(using= ToStringSerializer.class)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

}
