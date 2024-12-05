package com.garry.biscuit.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.garry.biscuit.common.enums.ResponseEnum;
import com.garry.biscuit.common.enums.UserRoleEnum;
import com.garry.biscuit.common.exception.BusinessException;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.util.JWTUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.UserLoginVo;
import com.garry.biscuit.user.domain.User;
import com.garry.biscuit.user.domain.UserExample;
import com.garry.biscuit.user.form.*;
import com.garry.biscuit.user.mapper.UserMapper;
import com.garry.biscuit.user.service.UserService;
import com.garry.biscuit.user.vo.UserModifyVo;
import com.garry.biscuit.user.vo.UserQueryVo;
import com.garry.biscuit.user.vo.UserRegisterVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Garry
 * 2024-12-04 09:57
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final String salt = "biscuit";

    @Resource
    private UserMapper userMapper;

    @Override
    public void save(UserSaveForm form) {
        User user = BeanUtil.copyProperties(form, User.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(user.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andUserAccountEqualTo(user.getUserAccount());
            if (userMapper.countByExample(userExample) > 0) {
                throw new BusinessException(ResponseEnum.USER_USER_ACCOUNT_EXIST);
            }
            // 对Id、createTime、updateTime 重新赋值
            long id = CommonUtil.getSnowflakeNextId();
            user.setId(id);
            user.setCreateTime(now);
            user.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            user.setUserName("biscuit_" + id);
            user.setUserExperience(0L);
            user.setUserLevel(0);
            user.setUserRole(UserRoleEnum.USER.getCode());
            userMapper.insert(user);
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

    @Override
    public UserRegisterVo register(UserRegisterForm form) {
        // 查询账号是否重复
        String userAccount = form.getUserAccount();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserAccountEqualTo(userAccount);
        if (userMapper.countByExample(userExample) > 0) {
            throw new BusinessException(ResponseEnum.USER_USER_ACCOUNT_EXIST);
        }
        // 密码加密
        String password = form.getUserPassword();
        String passwordMd5 = CommonUtil.md5Encryption(password + salt);
        form.setUserPassword(passwordMd5);
        // 插入用户
        UserSaveForm userSaveForm = BeanUtil.copyProperties(form, UserSaveForm.class);
        save(userSaveForm);
        // 通过唯一键 user_account 查找出刚刚插入的用户
        userExample = new UserExample();
        userExample.createCriteria().andUserAccountEqualTo(userAccount);
        User user = userMapper.selectByExample(userExample).get(0);
        return BeanUtil.copyProperties(user, UserRegisterVo.class);
    }

    @Override
    public UserLoginVo login(UserLoginForm form) {
        // 查询账号是否存在
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andUserAccountEqualTo(form.getUserAccount());
        List<User> users = userMapper.selectByExample(userExample);
        if (CollUtil.isEmpty(users)) {
            throw new BusinessException(ResponseEnum.USER_USER_ACCOUNT_NOT_EXIST);
        }
        // 校验密码
        String targetPassword = users.get(0).getUserPassword();
        if (!targetPassword.equals(CommonUtil.md5Encryption(form.getUserPassword() + salt))) {
            throw new BusinessException(ResponseEnum.USER_USER_PASSWORD_ERROR);
        }
        // 生成 JWT token
        UserLoginVo vo = BeanUtil.copyProperties(users.get(0), UserLoginVo.class);
        String token = JWTUtil.createToken(vo);
        vo.setToken(token);
        // 将 token 存入数据库

        return vo;
    }

    @Override
    public UserModifyVo modify(UserModifyForm form) {
        UserSaveForm userSaveForm = BeanUtil.copyProperties(form, UserSaveForm.class);
        save(userSaveForm);
        User user = profile(form.getId());
        return BeanUtil.copyProperties(user, UserModifyVo.class);
    }

    @Override
    public User profile(Long id) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdEqualTo(id);
        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    public void increaseExperience(UserIncreaseExperienceForm form) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdEqualTo(form.getId());
        User user = userMapper.selectByExample(userExample).get(0);
        user.setUserExperience(user.getUserExperience() + form.getExperienceIncreaseEnum().getExperience());
        userMapper.updateByExampleSelective(user, userExample);
    }
}
