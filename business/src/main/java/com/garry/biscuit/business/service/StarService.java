package com.garry.biscuit.business.service;

import com.garry.biscuit.business.domain.Product;
import com.garry.biscuit.business.form.StarQueryForm;
import com.garry.biscuit.business.form.StarSaveForm;
import com.garry.biscuit.business.form.StarStarsForm;
import com.garry.biscuit.business.vo.StarQueryVo;
import com.garry.biscuit.common.vo.PageVo;

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

    /**
     * 收藏
     */
    void star(Long userId, Long productId);

    /**
     * 取消收藏
     */
    void cancelStar(Long userId, Long productId);

    /**
     * 查询用户收藏数量
     */
    Integer countStar(Long userId);

    /**
     * 查询用户收藏列表，支持分页
     */
    PageVo<Product> stars(StarStarsForm form);
}
