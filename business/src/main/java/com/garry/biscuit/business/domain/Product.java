package com.garry.biscuit.business.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
@Document(indexName = "product")
public class Product {

    /**
     * id
     */
    @Id
    @Field(name = "id", type = FieldType.Long)
    private Long id;

    /**
     * 卖家id
     */
    @Field(name = "sellerId", type = FieldType.Long)
    private Long sellerId;

    /**
     * 标签id
     */
    @Field(name = "categoryId", type = FieldType.Long)
    private Long categoryId;

    /**
     * 标签名
     */
    @Field(name = "categoryName", type = FieldType.Keyword)
    private String categoryName;

    /**
     * 标题
     */
    // TODO 加上ik分词器，但是现在还没配置，因此会报错
    @Field(name = "title", type = FieldType.Text)
    private String title;

    /**
     * 商品详情
     */
    @Field(name = "detail", type = FieldType.Text)
    private String detail;

    /**
     * 图片
     */
    @Field(name = "picture", type = FieldType.Keyword)
    private String picture;

    /**
     * 成色|枚举[ProductFinenessEnum]
     */
    @Field(name = "fineness", type = FieldType.Integer)
    private Integer fineness;

    /**
     * 品牌
     */
    @Field(name = "brand", type = FieldType.Keyword)
    private String brand;

    /**
     * 型号
     */
    @Field(name = "model", type = FieldType.Keyword)
    private String model;

    /**
     * 价格
     */
    @Field(name = "price", type = FieldType.Keyword)
    private BigDecimal price;

    /**
     * 地点|枚举[ProductLocationEnum]
     */
    @Field(name = "location", type = FieldType.Integer)
    private Integer location;

    /**
     * 状态|枚举[ProductStatusEnum]
     */
    @Field(name = "status", type = FieldType.Integer)
    private Integer status;

    /**
     * 新增时间
     */
    @Field(name = "createTime", type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date createTime;

    /**
     * 修改时间
     */
    @Field(name = "updateTime", type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date updateTime;

}