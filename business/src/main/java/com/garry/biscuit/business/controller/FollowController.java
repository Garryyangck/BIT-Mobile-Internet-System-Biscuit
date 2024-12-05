package com.garry.biscuit.business.controller;

import com.garry.biscuit.business.service.FollowService;
import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.ResponseVo;
import jakarta.annotation.Resource;
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

    @RequestMapping(value = "/follow/{toId}", method = RequestMethod.POST)
    public ResponseVo follow(@PathVariable Long toId) {
        followService.follow(hostHolder.getUserId(), toId);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/count-follower", method = RequestMethod.GET)
    public ResponseVo<Integer> countFollower() {
        Integer countFollower = followService.countFollower(hostHolder.getUserId());
        return ResponseVo.success(countFollower);
    }

    @RequestMapping(value = "/count-followee", method = RequestMethod.GET)
    public ResponseVo<Integer> countFollowee() {
        Integer countFollowee = followService.countFollowee(hostHolder.getUserId());
        return ResponseVo.success(countFollowee);
    }

    @RequestMapping(value = "/cancel-follow/{toId}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long toId) {
        followService.cancelFollow(hostHolder.getUserId(), toId);
        return ResponseVo.success();
    }
}
