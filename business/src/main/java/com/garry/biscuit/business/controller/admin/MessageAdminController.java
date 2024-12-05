package com.garry.biscuit.business.controller.admin;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.business.form.MessageQueryForm;
import com.garry.biscuit.business.form.MessageSaveForm;
import com.garry.biscuit.business.service.MessageService;
import com.garry.biscuit.business.vo.MessageQueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@RestController
@RequestMapping(value = "/admin/message")
public class MessageAdminController {
    @Resource
    private MessageService messageService;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody MessageSaveForm form) {
        messageService.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<MessageQueryVo>> queryList(@Valid MessageQueryForm form) {
        PageVo<MessageQueryVo> vo = messageService.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        messageService.delete(id);
        return ResponseVo.success();
    }
}
