package com.garry.biscuit.business.controller;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.business.form.PreferenceQueryForm;
import com.garry.biscuit.business.form.PreferenceSaveForm;
import com.garry.biscuit.business.service.PreferenceService;
import com.garry.biscuit.business.vo.PreferenceQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 23:05
 */
@RestController
@RequestMapping(value = "/preference")
public class PreferenceController {
    @Resource
    private PreferenceService preferenceService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody PreferenceSaveForm form) {
        preferenceService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<PreferenceQueryVo>> queryList(@Valid PreferenceQueryForm form) {
        PageVo<PreferenceQueryVo> vo = preferenceService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        preferenceService.delete(id);
        return ResponseVo.success();
    }
}
