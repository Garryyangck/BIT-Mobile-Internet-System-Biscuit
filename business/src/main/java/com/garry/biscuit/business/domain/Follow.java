package com.garry.biscuit.business.domain;

import lombok.Data;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class Follow {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long fromId;

    /**
     * 被关注者id
     */
    private Long toId;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}