package com.garry.biscuit.business.controller.admin;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.business.form.FollowQueryForm;
import com.garry.biscuit.business.form.FollowSaveForm;
import com.garry.biscuit.business.service.FollowService;
import com.garry.biscuit.business.vo.FollowQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@RestController
@RequestMapping(value = "/admin/follow")
public class FollowAdminController {
    @Resource
    private FollowService followService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody FollowSaveForm form) {
        followService.save(form);
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
