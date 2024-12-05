package com.garry.biscuit.user.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Garry
 * 2024-12-05 16:49
 */
@Data
public class UserLoginForm {

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

}
