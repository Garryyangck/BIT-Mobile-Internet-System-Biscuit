package com.garry.biscuit.business.controller;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.business.form.ProductQueryForm;
import com.garry.biscuit.business.form.ProductSaveForm;
import com.garry.biscuit.business.service.ProductService;
import com.garry.biscuit.business.vo.ProductQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody ProductSaveForm form) {
        productService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<ProductQueryVo>> queryList(@Valid ProductQueryForm form) {
        PageVo<ProductQueryVo> vo = productService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseVo.success();
    }
}
