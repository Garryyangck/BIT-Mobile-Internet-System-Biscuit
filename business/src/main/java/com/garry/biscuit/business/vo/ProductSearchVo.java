package com.garry.biscuit.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-06 15:40
 */
@Data
public class ProductSearchVo {

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

    /**
     * 用户昵称
     */
    private String sellerName;

    /**
     * 用户头像
     */
    private String sellerAvatar;

    /**
     * 用户简介
     */
    private String sellerProfile;

    /**
     * 用户签名
     */
    private String sellerSignature;

    /**
     * 用户经验值
     */
    private Long sellerExperience;

    /**
     * 用户等级
     */
    private Integer sellerLevel;

    /**
     * 用户角色|枚举[UserRoleEnum]
     */
    private Integer sellerRole;
}
