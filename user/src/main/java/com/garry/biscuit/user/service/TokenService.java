package com.garry.biscuit.user.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.user.form.TokenQueryForm;
import com.garry.biscuit.user.form.TokenSaveForm;
import com.garry.biscuit.user.vo.TokenQueryVo;

/**
 * @author Garry
 * 2024-12-05 22:31
 */
public interface TokenService {
    /**
     * 插入新token，或修改已有的token
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(TokenSaveForm form);

    /**
     * 查询所有的token，支持分页
     */
    PageVo<TokenQueryVo> queryList(TokenQueryForm form);

    /**
     * 根据 id 删除token
     */
    void delete(Long id);
}
