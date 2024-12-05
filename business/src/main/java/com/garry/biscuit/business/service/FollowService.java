package com.garry.biscuit.business.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.FollowQueryForm;
import com.garry.biscuit.business.form.FollowSaveForm;
import com.garry.biscuit.business.vo.FollowQueryVo;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
public interface FollowService {
    /**
     * 插入新关注，或修改已有的关注
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(FollowSaveForm form);

    /**
     * 查询所有的关注，支持分页
     */
    PageVo<FollowQueryVo> queryList(FollowQueryForm form);

    /**
     * 根据 id 删除关注
     */
    void delete(Long id);
}
