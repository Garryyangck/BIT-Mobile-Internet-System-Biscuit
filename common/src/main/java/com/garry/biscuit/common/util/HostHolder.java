package com.garry.biscuit.common.util;


import com.garry.biscuit.common.common.ErrorCode;
import com.garry.biscuit.common.exception.BusinessException;
import com.garry.biscuit.common.model.response.UserLoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HostHolder {

    private final ThreadLocal<UserLoginResponse> localUsers = new ThreadLocal<>();

    public void setUser(UserLoginResponse vo) {
        localUsers.set(vo);
    }

    public UserLoginResponse getUser() {
        return localUsers.get();
    }

    public void remove() {
        localUsers.remove();
    }

    public Long getUserId() {
        try {
            return localUsers.get().getId();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
    }

    public String getUserRole() {
        try {
            return localUsers.get().getUserRole();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
    }
}
