package com.garry.biscuit.business.domain;

import lombok.Data;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class Conversation {

    /**
     * id
     */
    private Long id;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 买家id
     */
    private Long buyerId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品图片
     */
    private String productPicture;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}