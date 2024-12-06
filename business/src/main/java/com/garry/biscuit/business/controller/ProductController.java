package com.garry.biscuit.business.controller;

import com.garry.biscuit.business.elasticsearch.ProductElasticsearchRepository;
import com.garry.biscuit.business.form.ProductRecommendForm;
import com.garry.biscuit.business.form.ProductSaveForm;
import com.garry.biscuit.business.form.ProductSearchForm;
import com.garry.biscuit.business.mapper.ProductMapper;
import com.garry.biscuit.business.service.ProductService;
import com.garry.biscuit.business.vo.ProductDetailVo;
import com.garry.biscuit.business.vo.ProductRecommendVo;
import com.garry.biscuit.business.vo.ProductSearchVo;
import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 23:05
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Resource
    private ProductService productService;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private ProductMapper productMapper;

    @Autowired
    private ProductElasticsearchRepository productElasticsearchRepository;

    /**
     * 创建商品或编辑商品，自己是卖家
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody ProductSaveForm form) {
        form.setSellerId(hostHolder.getUser().getId());
        productService.save(form);
        return ResponseVo.success();
    }

    /**
     * 主页给用户推荐商品
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ResponseVo<PageVo<ProductRecommendVo>> recommend(@Valid ProductRecommendForm form) {
        form.setUserId(hostHolder.getUserId());
        PageVo<ProductRecommendVo> vo = productService.recommend(form);
        return ResponseVo.success(vo);
    }

    /**
     * 获取商品详情页信息
     */
    @RequestMapping(value = "/detail/{product_id}", method = RequestMethod.GET)
    public ResponseVo<ProductDetailVo> detail(@PathVariable("product_id") Long productId) {
        ProductDetailVo vo = productService.detail(productId);
        return ResponseVo.success(vo);
    }

    /**
     * 搜索
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseVo<PageVo<ProductSearchVo>> search(@Valid ProductSearchForm form) {
        PageVo<ProductSearchVo> vo = productService.search(form);
        return ResponseVo.success(vo);
    }
}
