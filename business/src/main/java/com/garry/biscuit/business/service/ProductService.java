package com.garry.biscuit.business.service;

import com.garry.biscuit.business.form.ProductQueryForm;
import com.garry.biscuit.business.form.ProductRecommendForm;
import com.garry.biscuit.business.form.ProductSaveForm;
import com.garry.biscuit.business.vo.ProductQueryVo;
import com.garry.biscuit.business.vo.ProductRecommendVo;
import com.garry.biscuit.common.vo.PageVo;

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
     * 管理员 only：查询所有的商品，支持分页
     */
    PageVo<ProductQueryVo> queryList(ProductQueryForm form);

    /**
     * 根据 id 删除商品
     */
    void delete(Long id);

    /**
     * 给用户推荐商品，支持分页
     * 先按照 id desc 分页，返回这一页偏好值最大的前一半商品
     */
    PageVo<ProductRecommendVo> recommend(ProductRecommendForm form);
}
