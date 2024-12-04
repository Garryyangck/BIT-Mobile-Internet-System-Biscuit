package com.garry.biscuit.user.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Garry
 * 2024-12-04 09:57
 */
@Data
public class UserSaveForm {

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    @NotBlank(message = "【账号】不能为空")
    private String userAccount;

    /**
     * 密码
     */
    @NotBlank(message = "【密码】不能为空")
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
    @NotNull(message = "【用户经验值】不能为空")
    private Long userExperience;

    /**
     * 用户等级
     */
    @NotNull(message = "【用户等级】不能为空")
    private Integer userLevel;

    /**
     * 用户角色|枚举[UserRoleEnum]
     */
    @NotNull(message = "【用户角色】不能为空")
    private Integer userRole;

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
