package com.garry.biscuit.business.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.CategoryQueryForm;
import com.garry.biscuit.business.form.CategorySaveForm;
import com.garry.biscuit.business.vo.CategoryQueryVo;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
public interface CategoryService {
    /**
     * 插入新标签，或修改已有的标签
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(CategorySaveForm form);

    /**
     * 查询所有的标签，支持分页
     */
    PageVo<CategoryQueryVo> queryList(CategoryQueryForm form);

    /**
     * 根据 id 删除标签
     */
    void delete(Long id);
}
