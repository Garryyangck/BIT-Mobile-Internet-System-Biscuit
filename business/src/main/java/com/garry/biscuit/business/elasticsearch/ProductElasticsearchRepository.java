package com.garry.biscuit.business.elasticsearch;

import com.garry.biscuit.business.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Garry
 * 2024-12-06 20:46
 */
public interface ProductElasticsearchRepository extends ElasticsearchRepository<Product, Long> {

    List<Product> findByTitleOrDetail(String title, String detail);
}
