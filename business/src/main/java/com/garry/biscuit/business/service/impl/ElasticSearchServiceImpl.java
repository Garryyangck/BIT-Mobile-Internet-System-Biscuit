package com.garry.biscuit.business.service.impl;

import com.garry.biscuit.business.domain.Product;
import com.garry.biscuit.business.domain.ProductExample;
import com.garry.biscuit.business.enums.ProductStatusEnum;
import com.garry.biscuit.business.mapper.ProductMapper;
import com.garry.biscuit.business.mapper.elasticsearch.ProductElasticsearchRepository;
import com.garry.biscuit.business.service.ElasticSearchService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Garry
 * 2024-12-06 21:16
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductElasticsearchRepository productElasticsearchRepository;

    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    @PostConstruct
    void init() {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andStatusEqualTo(ProductStatusEnum.NORMAL.getCode());
        List<Product> products = productMapper.selectByExample(productExample);
        productElasticsearchRepository.saveAll(products);
    }

    @Override
    public List<Product> searchProduct(String content) {
        // 同时对 title 和 detail 两个字段进行模糊搜索
        Query query = NativeQuery.builder().withQuery(q -> q
                .match(m -> m
                        .field("title")
                        .query(content)
                        .field("detail")
                        .query(content))).build();
        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class);
        ArrayList<Product> products = new ArrayList<>();
        searchHits.forEach(searchHit -> products.add(searchHit.getContent()));
        return products;
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
