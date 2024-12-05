package com.garry.biscuit.user.controller;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.common.vo.UserLoginVo;
import com.garry.biscuit.user.domain.User;
import com.garry.biscuit.user.form.UserLoginForm;
import com.garry.biscuit.user.form.UserModifyForm;
import com.garry.biscuit.user.form.UserRegisterForm;
import com.garry.biscuit.user.service.UserService;
import com.garry.biscuit.user.vo.UserModifyVo;
import com.garry.biscuit.user.vo.UserRegisterVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-03 23:48
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseVo<UserRegisterVo> register(@Valid @RequestBody UserRegisterForm form) {
        UserRegisterVo vo = userService.register(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseVo<UserLoginVo> login(@Valid @RequestBody UserLoginForm form) {
        UserLoginVo vo = userService.login(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseVo<UserModifyVo> save(@Valid @RequestBody UserModifyForm form) {
        form.setId(hostHolder.getUserId()); // 设置当前用户id
        UserModifyVo vo = userService.modify(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ResponseVo<User> queryList(@PathVariable Long id) {
        User user = userService.profile(id);
        return ResponseVo.success(user);
    }
}
