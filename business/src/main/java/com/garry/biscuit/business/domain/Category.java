package com.garry.biscuit.business.domain;

import lombok.Data;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class Category {

    /**
     * id
     */
    private Long id;

    /**
     * 标签名
     */
    private Long name;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}