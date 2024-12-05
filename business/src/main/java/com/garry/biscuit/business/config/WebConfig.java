package com.garry.biscuit.business.config;

import com.garry.biscuit.common.interceptor.LogIDInterceptor;
import com.garry.biscuit.common.interceptor.UserLoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LogIDInterceptor logIDInterceptor;

    @Resource
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logIDInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                );

        // 路径不要包含context-path(即最前面的：/user)
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/hello"
                );
    }
}
