package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.garry.biscuit.common.enums.ResponseEnum;
import com.garry.biscuit.common.enums.UserRoleEnum;
import com.garry.biscuit.common.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.MessageQueryForm;
import com.garry.biscuit.business.form.MessageSaveForm;
import com.garry.biscuit.business.mapper.MessageMapper;
import com.garry.biscuit.business.domain.Message;
import com.garry.biscuit.business.domain.MessageExample;
import com.garry.biscuit.business.service.MessageService;
import com.garry.biscuit.business.vo.MessageQueryVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Garry
 * 2024-12-06 13:16
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public void save(MessageSaveForm form) {
        Message message = BeanUtil.copyProperties(form, Message.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(message.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            message.setId(CommonUtil.getSnowflakeNextId());
            message.setCreateTime(now);
            message.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            message.setIsRead(0);
            messageMapper.insert(message);
            log.info("插入消息：{}", message);
        } else { // 修改
            message.setUpdateTime(now);
            messageMapper.updateByExampleSelective(message, new MessageExample());
            log.info("修改消息：{}", message);
        }
    }

    @Override
    public PageVo<MessageQueryVo> queryList(MessageQueryForm form) {
        if (UserRoleEnum.ADMIN.getCode().equals(form.getUserRole())) {
            MessageExample messageExample = new MessageExample();
            messageExample.setOrderByClause("update_time desc");
            PageHelper.startPage(form.getPageNum(), form.getPageSize());
            List<Message> messages = messageMapper.selectByExample(messageExample);
            PageInfo<Message> pageInfo = new PageInfo<>(messages);
            List<MessageQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), MessageQueryVo.class);
            PageVo<MessageQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
            vo.setList(voList);
            vo.setMsg("查询消息列表成功");
            return vo;
        } else if (UserRoleEnum.USER.getCode().equals(form.getUserRole())) {
            if (ObjUtil.isNull(form.getConversationId())) {
                throw new BusinessException(ResponseEnum.BUSINESS_CONVERSATION_ID_NULL);
            }
            MessageExample messageExample = new MessageExample();
            messageExample.createCriteria()
                    .andConversationIdEqualTo(form.getConversationId());
            messageExample.setOrderByClause("update_time desc");
            PageHelper.startPage(form.getPageNum(), form.getPageSize());
            List<Message> messages = messageMapper.selectByExample(messageExample);
            PageInfo<Message> pageInfo = new PageInfo<>(messages);
            List<MessageQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), MessageQueryVo.class);
            PageVo<MessageQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
            vo.setList(voList);
            vo.setMsg("查询消息列表成功");
            return vo;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andIdEqualTo(id);
        messageMapper.deleteByExample(messageExample);
    }
}
