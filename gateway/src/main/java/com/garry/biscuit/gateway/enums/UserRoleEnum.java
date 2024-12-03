package com.garry.biscuit.gateway.enums;

/**
 * @author Garry
 * 2024-12-03 20:52
 */
public enum UserRoleEnum {

    USER(0, "普通用户"),

    ADMIN(1, "管理员")
    ;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private final Integer code;

    private final String msg;

    UserRoleEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
