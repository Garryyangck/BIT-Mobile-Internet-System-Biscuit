package com.garry.biscuit.business.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.StarQueryForm;
import com.garry.biscuit.business.form.StarSaveForm;
import com.garry.biscuit.business.vo.StarQueryVo;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
public interface StarService {
    /**
     * 插入新收藏，或修改已有的收藏
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(StarSaveForm form);

    /**
     * 查询所有的收藏，支持分页
     */
    PageVo<StarQueryVo> queryList(StarQueryForm form);

    /**
     * 根据 id 删除收藏
     */
    void delete(Long id);
}
