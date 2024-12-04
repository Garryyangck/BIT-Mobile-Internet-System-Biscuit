package com.garry.biscuit.user.domain;

import lombok.Data;
import java.util.Date;

/**
 * @author Garry
 * 2024-12-04 09:57
 */
@Data
public class User {

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户签名
     */
    private String userSignature;

    /**
     * 用户经验值
     */
    private Long userExperience;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 用户角色|枚举[UserRoleEnum]
     */
    private Integer userRole;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}