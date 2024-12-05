package com.garry.biscuit.user.domain;

import lombok.Data;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 22:31
 */
@Data
public class Token {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * JWT
     */
    private String jwt;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}