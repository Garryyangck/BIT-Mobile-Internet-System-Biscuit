package com.garry.biscuit.business.controller.admin;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.business.form.StarQueryForm;
import com.garry.biscuit.business.form.StarSaveForm;
import com.garry.biscuit.business.service.StarService;
import com.garry.biscuit.business.vo.StarQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@RestController
@RequestMapping(value = "/admin/star")
public class StarAdminController {
    @Resource
    private StarService starService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody StarSaveForm form) {
        starService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<StarQueryVo>> queryList(@Valid StarQueryForm form) {
        PageVo<StarQueryVo> vo = starService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        starService.delete(id);
        return ResponseVo.success();
    }
}
