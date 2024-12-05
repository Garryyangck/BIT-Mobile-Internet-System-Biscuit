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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody ConversationSaveForm form) {
        conversationService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<ConversationQueryVo>> queryList(@Valid ConversationQueryForm form) {
        PageVo<ConversationQueryVo> vo = conversationService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        conversationService.delete(id);
        return ResponseVo.success();
    }
}
