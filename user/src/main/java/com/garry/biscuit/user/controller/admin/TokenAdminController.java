package com.garry.biscuit.user.controller.admin;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.user.form.TokenQueryForm;
import com.garry.biscuit.user.form.TokenSaveForm;
import com.garry.biscuit.user.service.TokenService;
import com.garry.biscuit.user.vo.TokenQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 22:31
 */
@RestController
@RequestMapping(value = "/admin/token")
public class TokenAdminController {
    @Resource
    private TokenService tokenService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody TokenSaveForm form) {
        tokenService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<TokenQueryVo>> queryList(@Valid TokenQueryForm form) {
        PageVo<TokenQueryVo> vo = tokenService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        tokenService.delete(id);
        return ResponseVo.success();
    }
}
