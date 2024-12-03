package com.garry.biscuit.common.exception;

import com.garry.biscuit.common.enums.ResponseEnum;

public class BusinessException extends RuntimeException {

    private final ResponseEnum responseEnum;

    public BusinessException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}
