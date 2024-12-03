package com.garry.biscuit.common.enums;

import lombok.Getter;

/**
 * 小于 1000 的 code 代表通用模块的异常
 * 以 1、2、3 开头代表异常来自相应的的模块
 */
@Getter
public enum ResponseEnum {

    ERROR(-1, "服务器异常"),

    SUCCESS(0, "操作成功"),

    PARAMETER_INPUT_ERROR(1, "参数输入异常"),

    DUPLICATE_KEY(2, "数据库唯一键异常"),

    API_ARGUMENT_MISMATCH(3, "接口参数不匹配"),

    HOSTHOLDER_NO_USER_FOUND(4, "hostholder未找到user"),

    NO_EXPIRE_CACHE_NAME_READ_FAILED(5, "no_expire_cache_name.txt文件读取异常")
    ;

    private final Integer code;

    private final String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
