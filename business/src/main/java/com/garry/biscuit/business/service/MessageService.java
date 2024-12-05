package com.garry.biscuit.business.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.MessageQueryForm;
import com.garry.biscuit.business.form.MessageSaveForm;
import com.garry.biscuit.business.vo.MessageQueryVo;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
public interface MessageService {
    /**
     * 插入新消息，或修改已有的消息
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(MessageSaveForm form);

    /**
     * 查询所有的消息，支持分页
     */
    PageVo<MessageQueryVo> queryList(MessageQueryForm form);

    /**
     * 根据 id 删除消息
     */
    void delete(Long id);
}
