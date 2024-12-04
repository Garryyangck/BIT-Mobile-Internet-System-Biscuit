package com.garry.biscuit.user.controller.admin;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.user.form.UserQueryForm;
import com.garry.biscuit.user.form.UserSaveForm;
import com.garry.biscuit.user.service.UserService;
import com.garry.biscuit.user.vo.UserQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-04 09:57
 */
@RestController
@RequestMapping(value = "/admin/user")
public class UserAdminController {
    @Resource
    private UserService userService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody UserSaveForm form) {
        userService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<UserQueryVo>> queryList(@Valid UserQueryForm form) {
        PageVo<UserQueryVo> vo = userService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseVo.success();
    }
}
