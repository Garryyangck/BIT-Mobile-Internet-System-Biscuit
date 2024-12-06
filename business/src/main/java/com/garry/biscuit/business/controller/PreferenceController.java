package com.garry.biscuit.business.controller;

import com.garry.biscuit.business.form.PreferenceModifyForm;
import com.garry.biscuit.business.service.PreferenceService;
import com.garry.biscuit.common.util.HostHolder;
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
@RequestMapping(value = "/preference")
public class PreferenceController {
    @Resource
    private PreferenceService preferenceService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseVo modify(@Valid @RequestBody PreferenceModifyForm form) {
        form.setUserId(hostHolder.getUser().getId());
        preferenceService.modify(form);
        return ResponseVo.success();
    }
}
