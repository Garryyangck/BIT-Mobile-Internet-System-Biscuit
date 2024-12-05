package com.garry.biscuit.business.controller.admin;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.business.form.CategoryQueryForm;
import com.garry.biscuit.business.form.CategorySaveForm;
import com.garry.biscuit.business.service.CategoryService;
import com.garry.biscuit.business.vo.CategoryQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@RestController
@RequestMapping(value = "/admin/category")
public class CategoryAdminController {
    @Resource
    private CategoryService categoryService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody CategorySaveForm form) {
        categoryService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<CategoryQueryVo>> queryList(@Valid CategoryQueryForm form) {
        PageVo<CategoryQueryVo> vo = categoryService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseVo.success();
    }
}
