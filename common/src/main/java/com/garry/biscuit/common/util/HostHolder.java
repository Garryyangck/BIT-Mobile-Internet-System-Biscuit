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
    private final ThreadLocal<UserLoginVo> members = new ThreadLocal<>();

    public void setMember(UserLoginVo vo) {
        members.set(vo);
    }

    public UserLoginVo getUser() {
        return members.get();
    }

    public void remove() {
        members.remove();
    }

    public Long getUserId() {
        try {
            return members.get().getId();
        } catch (Exception e) {
            log.error(ResponseEnum.HOSTHOLDER_NO_USER_FOUND.getMsg());
            throw new BusinessException(ResponseEnum.HOSTHOLDER_NO_USER_FOUND);
        }
    }
}
