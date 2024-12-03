package com.garry.biscuit.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.user.form.UserQueryForm;
import com.garry.biscuit.user.form.UserSaveForm;
import com.garry.biscuit.user.mapper.UserMapper;
import com.garry.biscuit.user.domain.User;
import com.garry.biscuit.user.domain.UserExample;
import com.garry.biscuit.user.service.UserService;
import com.garry.biscuit.user.vo.UserQueryVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Garry
 * 2024-12-04 00:06
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void save(UserSaveForm form) {
        User user = BeanUtil.copyProperties(form, User.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(user.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            user.setId(CommonUtil.getSnowflakeNextId());
            user.setCreateTime(now);
            user.setUpdateTime(now);
            userMapper.insert(user);
            // 可能还需要重新赋值其它的字段
            log.info("插入用户：{}", user);
        } else { // 修改
            user.setUpdateTime(now);
            userMapper.updateByExampleSelective(user, new UserExample());
            log.info("修改用户：{}", user);
        }
    }

    @Override
    public PageVo<UserQueryVo> queryList(UserQueryForm form) {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的用户
//        UserExample.Criteria criteria = userExample.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        List<UserQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), UserQueryVo.class);
        PageVo<UserQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询用户列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);
        userMapper.deleteByExample(userExample);
    }
}
