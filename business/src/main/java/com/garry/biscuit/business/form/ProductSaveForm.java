package com.garry.biscuit.business.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class ProductSaveForm {

    /**
     * id
     */
    private Long id;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 标签id
     */
    @NotNull(message = "【标签id】不能为空")
    private Long categoryId;

    /**
     * 标签名
     */
    @NotBlank(message = "【标签名】不能为空")
    private String categoryName;

    /**
     * 标题
     */
    @NotBlank(message = "【标题】不能为空")
    private String title;

    /**
     * 商品详情
     */
    @NotBlank(message = "【商品详情】不能为空")
    private String detail;

    /**
     * 图片
     */
    @NotBlank(message = "【图片】不能为空")
    private String picture;

    /**
     * 成色|枚举[ProductFinenessEnum]
     */
    private Integer fineness;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * 价格
     */
    @NotNull(message = "【价格】不能为空")
    private BigDecimal price;

    /**
     * 地点|枚举[ProductLocationEnum]
     */
    @NotNull(message = "【地点】不能为空")
    private Integer location;

    /**
     * 状态|枚举[ProductStatusEnum]
     */
    @NotNull(message = "【状态】不能为空")
    private Integer status;

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
