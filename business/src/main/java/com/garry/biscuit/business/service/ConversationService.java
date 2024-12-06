package com.garry.biscuit.business.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.ConversationQueryForm;
import com.garry.biscuit.business.form.ConversationSaveForm;
import com.garry.biscuit.business.vo.ConversationQueryVo;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
public interface ConversationService {
    /**
     * 插入新聊天，或修改已有的聊天
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(ConversationSaveForm form);

    /**
     * 查询所有的聊天，支持分页
     * 最后一条消息，消息的时间，对方的头像，对方的名字，商品的图片，未读消息数量
     * 如果 userId 是用户就用 ConversationUserQueryVo
     */
    PageVo<ConversationQueryVo> queryList(ConversationQueryForm form);

    /**
     * 根据 id 删除聊天
     */
    void delete(Long id);
}
