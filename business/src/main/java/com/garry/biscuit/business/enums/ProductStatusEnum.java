package com.garry.biscuit.business.enums;

/**
 * @author Garry
 * 2024-12-06 14:20
 */
public enum ProductStatusEnum {

    NORMAL(0, "正常"),

    SOLD(1, "已售出"),

    OFF_MARKET(2, "下架"),

    ;

    ProductStatusEnum(Integer code, String msg) {
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
