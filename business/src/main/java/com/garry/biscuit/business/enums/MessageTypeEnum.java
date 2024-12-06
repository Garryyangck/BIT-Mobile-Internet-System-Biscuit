package com.garry.biscuit.business.enums;

/**
 * @author Garry
 * 2024-12-06 13:44
 */
public enum MessageTypeEnum {

    TEXT(1, "文本消息"),

    IMAGE(2, "图片消息"),

    VOICE(3, "订单确认消息"),

    ;

    MessageTypeEnum(Integer code, String msg) {
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
