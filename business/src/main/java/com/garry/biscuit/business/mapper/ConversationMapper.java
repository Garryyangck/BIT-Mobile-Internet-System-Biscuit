package com.garry.biscuit.business.mapper;

import com.garry.biscuit.business.domain.Conversation;
import com.garry.biscuit.business.domain.ConversationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConversationMapper {
    long countByExample(ConversationExample example);

    int deleteByExample(ConversationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Conversation record);

    int insertSelective(Conversation record);

    List<Conversation> selectByExample(ConversationExample example);

    Conversation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Conversation record, @Param("example") ConversationExample example);

    int updateByExample(@Param("record") Conversation record, @Param("example") ConversationExample example);

    int updateByPrimaryKeySelective(Conversation record);

    int updateByPrimaryKey(Conversation record);
}