package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.FollowQueryForm;
import com.garry.biscuit.business.form.FollowSaveForm;
import com.garry.biscuit.business.mapper.FollowMapper;
import com.garry.biscuit.business.domain.Follow;
import com.garry.biscuit.business.domain.FollowExample;
import com.garry.biscuit.business.service.FollowService;
import com.garry.biscuit.business.vo.FollowQueryVo;
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
public class FollowServiceImpl implements FollowService {
    @Resource
    private FollowMapper followMapper;

    @Override
    public void save(FollowSaveForm form) {
        Follow follow = BeanUtil.copyProperties(form, Follow.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(follow.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            follow.setId(CommonUtil.getSnowflakeNextId());
            follow.setCreateTime(now);
            follow.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            followMapper.insert(follow);
            log.info("插入关注：{}", follow);
        } else { // 修改
            follow.setUpdateTime(now);
            followMapper.updateByExampleSelective(follow, new FollowExample());
            log.info("修改关注：{}", follow);
        }
    }

    @Override
    public PageVo<FollowQueryVo> queryList(FollowQueryForm form) {
        FollowExample followExample = new FollowExample();
        followExample.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的关注
//        FollowExample.Criteria criteria = followExample.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Follow> follows = followMapper.selectByExample(followExample);
        PageInfo<Follow> pageInfo = new PageInfo<>(follows);
        List<FollowQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), FollowQueryVo.class);
        PageVo<FollowQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询关注列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andIdEqualTo(id);
        followMapper.deleteByExample(followExample);
    }
}