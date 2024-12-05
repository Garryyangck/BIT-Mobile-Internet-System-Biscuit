package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.ConversationQueryForm;
import com.garry.biscuit.business.form.ConversationSaveForm;
import com.garry.biscuit.business.mapper.ConversationMapper;
import com.garry.biscuit.business.domain.Conversation;
import com.garry.biscuit.business.domain.ConversationExample;
import com.garry.biscuit.business.service.ConversationService;
import com.garry.biscuit.business.vo.ConversationQueryVo;
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
        ConversationExample conversationExample = new ConversationExample();
        conversationExample.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的聊天
//        ConversationExample.Criteria criteria = conversationExample.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Conversation> conversations = conversationMapper.selectByExample(conversationExample);
        PageInfo<Conversation> pageInfo = new PageInfo<>(conversations);
        List<ConversationQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), ConversationQueryVo.class);
        PageVo<ConversationQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询聊天列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        ConversationExample conversationExample = new ConversationExample();
        conversationExample.createCriteria().andIdEqualTo(id);
        conversationMapper.deleteByExample(conversationExample);
    }
}
