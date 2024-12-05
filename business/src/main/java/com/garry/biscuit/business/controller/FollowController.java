package com.garry.biscuit.business.controller;

import com.garry.biscuit.business.form.FollowFollowForm;
import com.garry.biscuit.business.form.FollowQueryForm;
import com.garry.biscuit.business.service.FollowService;
import com.garry.biscuit.business.vo.FollowQueryVo;
import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 23:05
 */
@RestController
@RequestMapping(value = "/follow")
public class FollowController {
    @Resource
    private FollowService followService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ResponseVo follow(@Valid @RequestBody FollowFollowForm form) {
        followService.follow(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<FollowQueryVo>> queryList(@Valid FollowQueryForm form) {
        PageVo<FollowQueryVo> vo = followService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        followService.delete(id);
        return ResponseVo.success();
    }
}
