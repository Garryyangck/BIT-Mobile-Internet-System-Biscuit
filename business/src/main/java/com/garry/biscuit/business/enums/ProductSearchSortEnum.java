package com.garry.biscuit.business.enums;

/**
 * @author Garry
 * 2024-12-06 15:46
 */
public enum ProductSearchSortEnum {

    NORMAL(0, "不排序"),

    PRICE_ASC(1, "价格升序"),

    PRICE_DESC(2, "价格降序"),

    FINENESS_ASC(3, "成色升序"),

    FINENESS_DESC(4, "成色降序"),

    ;

    ProductSearchSortEnum(Integer code, String msg) {
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
