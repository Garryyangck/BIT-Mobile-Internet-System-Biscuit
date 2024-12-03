package com.garry.biscuit.common.vo;

import com.garry.biscuit.common.enums.ResponseEnum;
import lombok.Data;

@Data
public class ResponseVo<T> {

    private boolean success;

    private Integer code;

    private String msg;

    private T data;

    public ResponseVo(boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseVo(Integer code, String msg, T data) {
        this(true, code, msg, data);
    }

    public ResponseVo(Integer code, String msg) {
        this(code, msg, null);
    }

    public ResponseVo(Integer code, String msg, boolean success) {
        this(success, code, msg, (T) null);
    }

    public static ResponseVo success() {
        return new ResponseVo(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), data);
    }

    public static ResponseVo error(ResponseEnum responseEnum) {
        return new ResponseVo(responseEnum.getCode(), responseEnum.getMsg(), false);
    }

    public static ResponseVo error(String errorMsg) {
        return new ResponseVo(ResponseEnum.ERROR.getCode(), errorMsg, false);
    }

    public static ResponseVo error(ResponseEnum responseEnum, String errorMsg) {
        return new ResponseVo(responseEnum.getCode(), errorMsg, false);
    }
}
