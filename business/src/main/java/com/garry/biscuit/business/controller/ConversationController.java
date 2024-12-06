package com.garry.biscuit.business.controller;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.business.form.ConversationQueryForm;
import com.garry.biscuit.business.form.ConversationSaveForm;
import com.garry.biscuit.business.service.ConversationService;
import com.garry.biscuit.business.vo.ConversationQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 23:05
 */
@RestController
@RequestMapping(value = "/conversation")
public class ConversationController {
    @Resource
    private ConversationService conversationService;

    @Resource
    private HostHolder hostHolder;

    /**
     * 创建对话，只能买家在购买商品页面与卖家创建对话，因此buyer是当前用户
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody ConversationSaveForm form) {
        form.setBuyerId(hostHolder.getUserId());
        conversationService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<ConversationQueryVo>> queryList(@Valid ConversationQueryForm form) {
        form.setUserId(hostHolder.getUserId());
        form.setUserRole(hostHolder.getUser().getUserRole());
        PageVo<ConversationQueryVo> vo = conversationService.queryList(form);
        return ResponseVo.success(vo);
    }
}
