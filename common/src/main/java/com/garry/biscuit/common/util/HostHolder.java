package com.garry.biscuit.common.util;

import com.garry.biscuit.common.enums.ResponseEnum;
import com.garry.biscuit.common.exception.BusinessException;
import com.garry.biscuit.common.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息，用于代替session对象
 * 该对象可以给调用它的每一个不同的线程，
 * 单独分配一个ThreadLocalMap用于set User。
 */
@Slf4j
@Component
public class HostHolder {

    private final ThreadLocal<UserLoginVo> users = new ThreadLocal<>();

    public void setUser(UserLoginVo vo) {
        users.set(vo);
    }

    public UserLoginVo getUser() {
        return users.get();
    }

    public void remove() {
        users.remove();
    }

    public Long getUserId() {
        try {
            return users.get().getId();
        } catch (Exception e) {
            log.error(ResponseEnum.HOSTHOLDER_NO_USER_FOUND.getMsg());
            throw new BusinessException(ResponseEnum.HOSTHOLDER_NO_USER_FOUND);
        }
    }
}
