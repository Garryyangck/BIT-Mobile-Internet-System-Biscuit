package com.garry.biscuit.user.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.user.form.UserQueryForm;
import com.garry.biscuit.user.form.UserRegisterForm;
import com.garry.biscuit.user.form.UserSaveForm;
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


}
