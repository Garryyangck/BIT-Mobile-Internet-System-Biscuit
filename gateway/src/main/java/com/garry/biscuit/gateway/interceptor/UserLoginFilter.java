package com.garry.biscuit.gateway.interceptor;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.garry.biscuit.gateway.enums.UserRoleEnum;
import com.garry.biscuit.gateway.util.JWTUtil;
import com.garry.biscuit.gateway.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 登录校验拦截器
 */
@SuppressWarnings("LoggingSimilarMessage")
@Slf4j
@Component
public class UserLoginFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("------------- UserLoginFilter 开始 {} -------------", path);

        // 排除不需要过滤的接口
        if (path.contains("/hello")
                || path.contains("/register")
                || path.contains("/login")) {
            log.info("{} 不需要登录", path);
        } else {
            String token = exchange.getRequest().getHeaders().getFirst("token");
            log.info("用户登录验证开始，token = {}", token);
            if (StrUtil.isBlank(token) || !JWTUtil.validate(token)) {
                log.info("token为空、无效或已过期");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); // 401 状态码
                log.info("------------- 结束 {} -------------\n", path);
                return exchange.getResponse().setComplete();
            } else {
                if (path.contains("/admin")) {
                    log.info("需要管理员");
                    JSONObject userObject = JWTUtil.getJSONObject(token);
                    UserLoginVo userLoginVo = JSONUtil.toBean(userObject, UserLoginVo.class);
                    if (ObjUtil.isNull(userLoginVo) || !UserRoleEnum.ADMIN.getCode().equals(userLoginVo.getUserRole())) { // 判断用户角色是否为管理员
                        log.info("用户不是管理员，无权限访问");
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); // 401 状态码
                        log.info("------------- 结束 {} -------------\n", path);
                        return exchange.getResponse().setComplete();
                    }
                }
                log.info("登录校验通过");
            }
        }

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 在对接口的请求完成之后执行的逻辑
            // ...
            log.info("------------- 结束 {} -------------\n", path);
        }));
    }

    @Override
    public int getOrder() {
        return -1; // 设置过滤器的优先级，数字越小优先级越高
    }
}