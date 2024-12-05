package com.garry.biscuit.business.domain;

import lombok.Data;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class Preference {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标签id
     */
    private Long categoryId;

    /**
     * 偏好值
     */
    private Integer number;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}