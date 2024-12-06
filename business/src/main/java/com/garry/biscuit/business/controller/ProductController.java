package com.garry.biscuit.business.controller;

import com.garry.biscuit.business.form.ProductRecommendForm;
import com.garry.biscuit.business.form.ProductSaveForm;
import com.garry.biscuit.business.service.ProductService;
import com.garry.biscuit.business.vo.ProductRecommendVo;
import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
