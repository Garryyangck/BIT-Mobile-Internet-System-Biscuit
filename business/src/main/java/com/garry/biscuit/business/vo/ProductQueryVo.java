package com.garry.biscuit.business.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class ProductQueryVo {

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
     * 标签id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long categoryId;

    /**
     * 标签名
     */
    private String categoryName;

    /**
     * 标题
     */
    private String title;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 图片
     */
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
    private BigDecimal price;

    /**
     * 地点|枚举[ProductLocationEnum]
     */
    private Integer location;

    /**
     * 状态|枚举[ProductStatusEnum]
     */
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
