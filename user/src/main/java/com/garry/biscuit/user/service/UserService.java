package com.garry.biscuit.user.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.user.form.UserQueryForm;
import com.garry.biscuit.user.form.UserSaveForm;
import com.garry.biscuit.user.vo.UserQueryVo;

/**
 * @author Garry
 * 2024-12-03 23:52
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
}