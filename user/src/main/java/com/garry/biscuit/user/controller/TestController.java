package com.garry.biscuit.user.controller;

import com.garry.biscuit.common.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 允许随Nacos动态更新
public class TestController {

    @Value("${test.nacos}")
    private String testNacos;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseVo<String> hello() {
        return ResponseVo.success("hello user! " + testNacos);
    }
}
