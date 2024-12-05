package com.garry.biscuit.gateway.util;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTUtil {

    private static final String key = "theBravestGarry20240201";

    /**
     * 校验 token 是否有效
     */
    public static boolean validate(String token) {
        try {
            JWT jwt = JWT.of(token).setKey(key.getBytes());
            return jwt.validate(0);
        } catch (Exception e) {
            log.error("JWT校验异常", e);
            return false;
        }
    }

    /**
     * 获取 JWT 中的原始内容
     * 若 token 无效，则返回 null
     */
    public static JSONObject getJSONObject(String token) {
        if (validate(token)) {
            JWT jwt = JWT.of(token).setKey(key.getBytes());
            JSONObject payloads = jwt.getPayloads();
            payloads.remove(JWTPayload.ISSUED_AT);
            payloads.remove(JWTPayload.EXPIRES_AT);
            payloads.remove(JWTPayload.NOT_BEFORE);
            log.info("根据 token 获取的原始内容: {}", payloads);
            return payloads;
        } else {
            log.error("token 无效或已过期，无法获取 token.payloads");
            return null;
        }
    }
}
