package com.garry.biscuit.business.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class ConversationSaveForm {

    /**
     * id
     */
    private Long id;

    /**
     * 卖家id
     */
    @NotNull(message = "【卖家id】不能为空")
    private Long sellerId;

    /**
     * 买家id
     */
    private Long buyerId;

    /**
     * 商品id
     */
    @NotNull(message = "【商品id】不能为空")
    private Long productId;

    /**
     * 商品图片
     */
    @NotBlank(message = "【商品图片】不能为空")
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

}
