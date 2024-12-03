package com.garry.biscuit.gateway.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UserLoginVo {

    @JsonSerialize(using = ToStringSerializer.class) // 解决 Long 类型精度丢失的问题
    private Long id;

    private String mobile;

    private String token;

    private Integer role;
}
