package com.garry.biscuit.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.user.form.TokenQueryForm;
import com.garry.biscuit.user.form.TokenSaveForm;
import com.garry.biscuit.user.mapper.TokenMapper;
import com.garry.biscuit.user.domain.Token;
import com.garry.biscuit.user.domain.TokenExample;
import com.garry.biscuit.user.service.TokenService;
import com.garry.biscuit.user.vo.TokenQueryVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Garry
 * 2024-12-05 22:31
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private TokenMapper tokenMapper;

    @Override
    public void save(TokenSaveForm form) {
        Token token = BeanUtil.copyProperties(form, Token.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(token.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            token.setId(CommonUtil.getSnowflakeNextId());
            token.setCreateTime(now);
            token.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            tokenMapper.insert(token);
            log.info("插入token：{}", token);
        } else { // 修改
            token.setUpdateTime(now);
            tokenMapper.updateByExampleSelective(token, new TokenExample());
            log.info("修改token：{}", token);
        }
    }

    @Override
    public PageVo<TokenQueryVo> queryList(TokenQueryForm form) {
        TokenExample tokenExample = new TokenExample();
        tokenExample.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的token
//        TokenExample.Criteria criteria = tokenExample.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Token> tokens = tokenMapper.selectByExample(tokenExample);
        PageInfo<Token> pageInfo = new PageInfo<>(tokens);
        List<TokenQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), TokenQueryVo.class);
        PageVo<TokenQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询token列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        TokenExample tokenExample = new TokenExample();
        tokenExample.createCriteria().andIdEqualTo(id);
        tokenMapper.deleteByExample(tokenExample);
    }
}
