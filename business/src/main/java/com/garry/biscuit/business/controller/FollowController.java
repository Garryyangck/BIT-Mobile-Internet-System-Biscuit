package com.garry.biscuit.business.controller;

import com.garry.biscuit.business.form.FollowCountFolloweeForm;
import com.garry.biscuit.business.form.FollowCountFollowerForm;
import com.garry.biscuit.business.form.FollowFollowForm;
import com.garry.biscuit.business.service.FollowService;
import com.garry.biscuit.common.util.HostHolder;
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
        form.setFromId(hostHolder.getUserId());
        followService.follow(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/count-follower", method = RequestMethod.GET)
    public ResponseVo<Integer> countFollower(@Valid FollowCountFollowerForm form) {
        form.setToId(hostHolder.getUserId());
        Integer countFollower = followService.countFollower(form);
        return ResponseVo.success(countFollower);
    }

    @RequestMapping(value = "/count-followee", method = RequestMethod.GET)
    public ResponseVo<Integer> countFollowee(@Valid FollowCountFolloweeForm form) {
        form.setFromId(hostHolder.getUserId());
        Integer countFollowee = followService.countFollowee(form);
        return ResponseVo.success(countFollowee);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        followService.delete(id);
        return ResponseVo.success();
    }
}
