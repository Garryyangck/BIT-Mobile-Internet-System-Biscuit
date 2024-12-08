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

    NO_EXPIRE_CACHE_NAME_READ_FAILED(5, "no_expire_cache_name.txt文件读取异常"),

    USER_USER_ACCOUNT_EXIST(1001, "账号已存在，注册失败"),

    USER_USER_ACCOUNT_NOT_EXIST(1002, "该账号不存在，登录失败"),

    USER_USER_PASSWORD_ERROR(1003, "密码错误，登录失败"),

    BUSINESS_FOLLOW_ALREADY_FOLLOWED(2001, "已经关注过该用户了"),

    BUSINESS_FOLLOW_NOT_FOLLOWED(2002, "未关注该用户，取消关注失败"),

    BUSINESS_STAR_ALREADY_EXIST(3001, "已经收藏该商品，无法重复收藏"),

    BUSINESS_STAR_NOT_EXIST(3002, "未收藏该商品，取消收藏失败"),

    BUSINESS_CONVERSATION_ID_NULL(4001, "聊天室id不能为空"),

    ;

    private final Integer code;

    private final String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
