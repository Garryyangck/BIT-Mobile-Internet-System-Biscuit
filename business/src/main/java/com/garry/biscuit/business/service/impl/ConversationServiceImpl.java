package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.garry.biscuit.business.domain.Conversation;
import com.garry.biscuit.business.domain.ConversationExample;
import com.garry.biscuit.business.domain.Message;
import com.garry.biscuit.business.domain.MessageExample;
import com.garry.biscuit.business.feign.UserFeign;
import com.garry.biscuit.business.form.ConversationQueryForm;
import com.garry.biscuit.business.form.ConversationSaveForm;
import com.garry.biscuit.business.mapper.ConversationMapper;
import com.garry.biscuit.business.mapper.MessageMapper;
import com.garry.biscuit.business.service.ConversationService;
import com.garry.biscuit.business.vo.ConversationQueryVo;
import com.garry.biscuit.common.domain.User;
import com.garry.biscuit.common.enums.UserRoleEnum;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Slf4j
@Service
public class ConversationServiceImpl implements ConversationService {
    @Resource
    private ConversationMapper conversationMapper;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserFeign userFeign;

    @Override
    public void save(ConversationSaveForm form) {
        Conversation conversation = BeanUtil.copyProperties(form, Conversation.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(conversation.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            conversation.setId(CommonUtil.getSnowflakeNextId());
            conversation.setCreateTime(now);
            conversation.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            conversationMapper.insert(conversation);
            log.info("插入聊天：{}", conversation);
        } else { // 修改
            conversation.setUpdateTime(now);
            conversationMapper.updateByExampleSelective(conversation, new ConversationExample());
            log.info("修改聊天：{}", conversation);
        }
    }

    @Override
    public PageVo<ConversationQueryVo> queryList(ConversationQueryForm form) {
        if (UserRoleEnum.ADMIN.getCode().equals(form.getUserRole())) {
            ConversationExample conversationExample = new ConversationExample();
            conversationExample.setOrderByClause("id desc");
            PageHelper.startPage(form.getPageNum(), form.getPageSize());
            List<Conversation> conversations = conversationMapper.selectByExample(conversationExample);
            PageInfo<Conversation> pageInfo = new PageInfo<>(conversations);
            List<ConversationQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), ConversationQueryVo.class);
            PageVo<ConversationQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
            vo.setList(voList);
            vo.setMsg("查询聊天列表成功");
            return vo;
        } else if (UserRoleEnum.USER.getCode().equals(form.getUserRole())) { // 普通用户只能查询自己有关的聊天
            ConversationExample conversationExample = new ConversationExample();
            conversationExample.createCriteria().andSellerIdEqualTo(form.getUserId());
            conversationExample.or().andBuyerIdEqualTo(form.getUserId()); // WHERE seller_id = ? OR buyer_id = ?
            conversationExample.setOrderByClause("id desc");
            PageHelper.startPage(form.getPageNum(), form.getPageSize());
            List<Conversation> conversations = conversationMapper.selectByExample(conversationExample);
            PageInfo<Conversation> pageInfo = new PageInfo<>(conversations);
            List<ConversationQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), ConversationQueryVo.class);
            // 为 voList 的每一个元素独特的字段赋值
            for (ConversationQueryVo vo : voList) {
                // 添加用户名字和头像
                Long chatterId = vo.getSellerId().equals(form.getUserId()) ? vo.getBuyerId() : vo.getSellerId();
                ResponseVo<User> userResponseVo = userFeign.queryUserById(chatterId);
                vo.setChatterName(userResponseVo.getData().getUserName());
                vo.setChatterAvatar(userResponseVo.getData().getUserAvatar());
                // 添加 Message 相关
                MessageExample messageExample = new MessageExample();
                messageExample.createCriteria()
                        .andConversationIdEqualTo(vo.getId())
                        .andToIdEqualTo(form.getUserId())
                        .andIsReadEqualTo(0);
                long count = messageMapper.countByExample(messageExample);
                vo.setUnreadCount((int) count);
                messageExample = new MessageExample();
                messageExample.createCriteria().andConversationIdEqualTo(vo.getId());
                messageExample.setOrderByClause("id desc");
                Message lastMessage = messageMapper.selectByExample(messageExample).get(0);
                vo.setLastMessage(lastMessage.getContent());
                vo.setLastMessageTime(lastMessage.getUpdateTime());
            }
            PageVo<ConversationQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
            vo.setList(voList);
            return vo;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        ConversationExample conversationExample = new ConversationExample();
        conversationExample.createCriteria().andIdEqualTo(id);
        conversationMapper.deleteByExample(conversationExample);
    }
}
