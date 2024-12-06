package com.garry.biscuit.business.controller;

import com.garry.biscuit.business.domain.Product;
import com.garry.biscuit.business.form.StarStarsForm;
import com.garry.biscuit.business.service.StarService;
import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Garry
 * 2024-12-05 23:05
 */
@RestController
@RequestMapping(value = "/star")
public class StarController {
    @Resource
    private StarService starService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/star/{product_id}", method = RequestMethod.POST)
    public ResponseVo star(@PathVariable("product_id") Long productId) {
        starService.star(hostHolder.getUserId(), productId);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/count-star", method = RequestMethod.GET)
    public ResponseVo<Integer> countStar() {
        Integer countStar = starService.countStar(hostHolder.getUserId());
        return ResponseVo.success(countStar);
    }

    @RequestMapping(value = "/cancel-star/{product_id}", method = RequestMethod.DELETE)
    public ResponseVo cancelStar(@PathVariable("product_id") Long productId) {
        starService.cancelStar(hostHolder.getUserId(), productId);
        return ResponseVo.success();
    }
    
    @RequestMapping(value = "/stars", method = RequestMethod.GET)
    public ResponseVo<PageVo<Product>> stars(@Valid StarStarsForm form) {
        form.setUserId(hostHolder.getUserId());
        PageVo<Product> vo = starService.stars(form);
        return ResponseVo.success(vo);
    }
}
