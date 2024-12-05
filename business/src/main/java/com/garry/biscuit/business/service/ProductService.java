package com.garry.biscuit.business.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.ProductQueryForm;
import com.garry.biscuit.business.form.ProductSaveForm;
import com.garry.biscuit.business.vo.ProductQueryVo;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
public interface ProductService {
    /**
     * 插入新商品，或修改已有的商品
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(ProductSaveForm form);

    /**
     * 查询所有的商品，支持分页
     */
    PageVo<ProductQueryVo> queryList(ProductQueryForm form);

    /**
     * 根据 id 删除商品
     */
    void delete(Long id);
}
