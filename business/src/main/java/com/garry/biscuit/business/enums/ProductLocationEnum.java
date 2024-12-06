package com.garry.biscuit.business.enums;

/**
 * @author Garry
 * 2024-12-06 14:19
 */
public enum ProductLocationEnum {

    JING_YUAN(0, "静园"),

    ;

    ProductLocationEnum(Integer code, String msg) {
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
