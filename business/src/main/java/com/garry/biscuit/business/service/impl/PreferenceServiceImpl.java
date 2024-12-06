package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.garry.biscuit.business.domain.Preference;
import com.garry.biscuit.business.domain.PreferenceExample;
import com.garry.biscuit.business.form.PreferenceModifyForm;
import com.garry.biscuit.business.form.PreferenceQueryForm;
import com.garry.biscuit.business.form.PreferenceSaveForm;
import com.garry.biscuit.business.mapper.PreferenceMapper;
import com.garry.biscuit.business.service.PreferenceService;
import com.garry.biscuit.business.vo.PreferenceQueryVo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
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
public class PreferenceServiceImpl implements PreferenceService {
    @Resource
    private PreferenceMapper preferenceMapper;

    @Override
    public void save(PreferenceSaveForm form) {
        Preference preference = BeanUtil.copyProperties(form, Preference.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(preference.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            preference.setId(CommonUtil.getSnowflakeNextId());
            preference.setCreateTime(now);
            preference.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            preferenceMapper.insert(preference);
            log.info("插入偏好：{}", preference);
        } else { // 修改
            preference.setUpdateTime(now);
            preferenceMapper.updateByExampleSelective(preference, new PreferenceExample());
            log.info("修改偏好：{}", preference);
        }
    }

    @Override
    public PageVo<PreferenceQueryVo> queryList(PreferenceQueryForm form) {
        PreferenceExample preferenceExample = new PreferenceExample();
        preferenceExample.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的偏好
//        PreferenceExample.Criteria criteria = preferenceExample.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Preference> preferences = preferenceMapper.selectByExample(preferenceExample);
        PageInfo<Preference> pageInfo = new PageInfo<>(preferences);
        List<PreferenceQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), PreferenceQueryVo.class);
        PageVo<PreferenceQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询偏好列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        PreferenceExample preferenceExample = new PreferenceExample();
        preferenceExample.createCriteria().andIdEqualTo(id);
        preferenceMapper.deleteByExample(preferenceExample);
    }

    @Override
    public void modify(PreferenceModifyForm form) {
        // 查出来
        PreferenceExample preferenceExample = new PreferenceExample();
        preferenceExample.createCriteria()
                .andUserIdEqualTo(form.getUserId())
                .andCategoryIdEqualTo(form.getCategoryId());
        Preference preference = preferenceMapper.selectByExample(preferenceExample).get(0);
        // 修改
        preference.setNumber(preference.getNumber() + form.getModifyNumber());
        PreferenceSaveForm saveForm = BeanUtil.copyProperties(preference, PreferenceSaveForm.class);
        save(saveForm);
    }
}
