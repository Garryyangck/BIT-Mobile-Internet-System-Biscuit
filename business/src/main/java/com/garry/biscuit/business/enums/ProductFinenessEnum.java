package com.garry.biscuit.business.enums;

/**
 * @author Garry
 * 2024-12-06 14:17
 */
public enum ProductFinenessEnum {

    NEW(0, "新品"),

    OLD(1, "旧物"),

    ;

    ProductFinenessEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private Integer code;

    private String msg;


}
