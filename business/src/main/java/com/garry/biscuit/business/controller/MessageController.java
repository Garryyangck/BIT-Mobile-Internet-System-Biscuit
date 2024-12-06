package com.garry.biscuit.business.controller;

import com.garry.biscuit.business.form.MessageQueryForm;
import com.garry.biscuit.business.form.MessageSaveForm;
import com.garry.biscuit.business.service.MessageService;
import com.garry.biscuit.business.vo.MessageQueryVo;
import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
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
@RequestMapping(value = "/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @Resource
    private HostHolder hostHolder;

    /**
     * 消息的发送者，肯定是当前的用户
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody MessageSaveForm form) {
        form.setFromId(hostHolder.getUserId());
        messageService.save(form);
        return ResponseVo.success();
    }

    /**
     * 不采用 websocket，而是前端轮询此接口
     */
    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<MessageQueryVo>> queryList(@Valid MessageQueryForm form) {
        form.setUserRole(hostHolder.getUser().getUserRole());
        PageVo<MessageQueryVo> vo = messageService.queryList(form);
        return ResponseVo.success(vo);
    }
}
