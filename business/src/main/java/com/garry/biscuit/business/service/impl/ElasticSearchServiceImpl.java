package com.garry.biscuit.business.service.impl;

import com.garry.biscuit.business.domain.Product;
import com.garry.biscuit.business.domain.ProductExample;
import com.garry.biscuit.business.enums.ProductStatusEnum;
import com.garry.biscuit.business.mapper.ProductMapper;
import com.garry.biscuit.business.elasticsearch.ProductElasticsearchRepository;
import com.garry.biscuit.business.service.ElasticSearchService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Garry
 * 2024-12-06 21:16
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Resource
    private ProductMapper productMapper;

    @Autowired
    private ProductElasticsearchRepository productElasticsearchRepository;

    @PostConstruct
    void init() {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andStatusEqualTo(ProductStatusEnum.NORMAL.getCode());
        List<Product> products = productMapper.selectByExample(productExample);
        productElasticsearchRepository.saveAll(products);
    }

    @Override
    public List<Product> searchProduct(String content) {
        return productElasticsearchRepository.findByTitleOrDetail(content, content);
    }

    @Override
    public void saveProduct(Product product) {
        productElasticsearchRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productElasticsearchRepository.deleteById(id);
    }
}
