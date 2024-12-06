package com.garry.biscuit.business.service;

import com.garry.biscuit.business.domain.Product;

import java.util.List;

/**
 * @author Garry
 * 2024-12-06 21:13
 */
public interface ElasticSearchService {

    /**
     * 搜索商品，全部返回
     */
    List<Product> searchProduct(String content);

    /**
     * 添加或更新 ES 的d商品
     */
    void saveProduct(Product product);

    /**
     * 删除 ES 的商品
     */
    void deleteProduct(Long id);
}
