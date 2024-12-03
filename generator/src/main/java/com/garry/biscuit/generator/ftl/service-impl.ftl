package com.garry.biscuit.${module}.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.${module}.form.${Domain}QueryForm;
import com.garry.biscuit.${module}.form.${Domain}SaveForm;
import com.garry.biscuit.${module}.mapper.${Domain}Mapper;
import com.garry.biscuit.${module}.domain.${Domain};
import com.garry.biscuit.${module}.domain.${Domain}Example;
import com.garry.biscuit.${module}.service.${Domain}Service;
import com.garry.biscuit.${module}.vo.${Domain}QueryVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Garry
 * ${DateTime}
 */
@Slf4j
@Service
public class ${Domain}ServiceImpl implements ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    @Override
    public void save(${Domain}SaveForm form) {
        ${Domain} ${domain} = BeanUtil.copyProperties(form, ${Domain}.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(${domain}.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            ${domain}.setId(CommonUtil.getSnowflakeNextId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
            // 可能还需要重新赋值其它的字段
            log.info("插入${tableNameCn}：{}", ${domain});
        } else { // 修改
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.updateByExampleSelective(${domain}, new ${Domain}Example());
            log.info("修改${tableNameCn}：{}", ${domain});
        }
    }

    @Override
    public PageVo<${Domain}QueryVo> queryList(${Domain}QueryForm form) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的${tableNameCn}
//        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);
        List<${Domain}QueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), ${Domain}QueryVo.class);
        PageVo<${Domain}QueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询${tableNameCn}列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.createCriteria().andIdEqualTo(id);
        ${domain}Mapper.deleteByExample(${domain}Example);
    }
}
