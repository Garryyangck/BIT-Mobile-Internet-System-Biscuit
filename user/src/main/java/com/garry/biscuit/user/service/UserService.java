package com.garry.biscuit.user.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.UserLoginVo;
import com.garry.biscuit.user.domain.User;
import com.garry.biscuit.user.form.*;
import com.garry.biscuit.user.vo.UserModifyVo;
import com.garry.biscuit.user.vo.UserQueryVo;
import com.garry.biscuit.user.vo.UserRegisterVo;

/**
 * @author Garry
 * 2024-12-04 09:57
 */
public interface UserService {
    /**
     * 插入新用户，或修改已有的用户
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(UserSaveForm form);

    /**
     * 查询所有的用户，支持分页
     */
    PageVo<UserQueryVo> queryList(UserQueryForm form);

    /**
     * 根据 id 删除用户
     */
    void delete(Long id);

    /**
     * 通过账号和密码注册
     * 存储密码时需要增加盐值
     */
    UserRegisterVo register(UserRegisterForm form);

    /**
     * 通过账号和密码登录
     * 返回 JWT token
     */
    UserLoginVo login(UserLoginForm form);

    /**
     * 用户修改个人信息
     * 不能修改账号和密码
     */
    UserModifyVo modify(UserModifyForm form);

    /**
     * 获取用户信息
     */
    User profile(Long id);

    /**
     * 增加用户经验值
     */
    void increaseExperience(UserIncreaseExperienceForm form);
}
