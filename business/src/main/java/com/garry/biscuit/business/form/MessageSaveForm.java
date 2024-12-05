package com.garry.biscuit.business.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class MessageSaveForm {

    /**
     * id
     */
    private Long id;

    /**
     * 发出者id
     */
    @NotNull(message = "【发出者id】不能为空")
    private Long fromId;

    /**
     * 接收者id
     */
    @NotNull(message = "【接收者id】不能为空")
    private Long toId;

    /**
     * 商品id
     */
    @NotNull(message = "【商品id】不能为空")
    private Long productId;

    /**
     * 内容
     */
    @NotBlank(message = "【内容】不能为空")
    private String content;

    /**
     * 消息类型|枚举[MessageTypeEnum]
     */
    @NotNull(message = "【消息类型】不能为空")
    private Integer type;

    /**
     * 是否已读，0-未读，1-已读
     */
    @NotNull(message = "【是否已读，0-未读，1-已读】不能为空")
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
