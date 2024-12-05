package com.garry.biscuit.user.enums;

/**
 * @author Garry
 * 2024-12-05 22:11
 */
public enum UserExperienceIncreaseEnum {

    LOGIN(1, "登录", 1L),

    DEAL(2, "交易", 5L),

    ;

    UserExperienceIncreaseEnum(Integer code, String msg, Long experience) {
        this.code = code;
        this.msg = msg;
        this.experience = experience;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Long getExperience() {
        return experience;
    }

    private Integer code;

    private String msg;

    private Long experience;
}
