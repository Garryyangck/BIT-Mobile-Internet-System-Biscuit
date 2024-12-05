package com.garry.biscuit.business.mapper;

import com.garry.biscuit.business.domain.Preference;
import com.garry.biscuit.business.domain.PreferenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PreferenceMapper {
    long countByExample(PreferenceExample example);

    int deleteByExample(PreferenceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Preference record);

    int insertSelective(Preference record);

    List<Preference> selectByExample(PreferenceExample example);

    Preference selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Preference record, @Param("example") PreferenceExample example);

    int updateByExample(@Param("record") Preference record, @Param("example") PreferenceExample example);

    int updateByPrimaryKeySelective(Preference record);

    int updateByPrimaryKey(Preference record);
}