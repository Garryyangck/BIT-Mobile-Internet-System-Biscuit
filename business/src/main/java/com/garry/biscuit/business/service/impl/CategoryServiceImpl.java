package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.CategoryQueryForm;
import com.garry.biscuit.business.form.CategorySaveForm;
import com.garry.biscuit.business.mapper.CategoryMapper;
import com.garry.biscuit.business.domain.Category;
import com.garry.biscuit.business.domain.CategoryExample;
import com.garry.biscuit.business.service.CategoryService;
import com.garry.biscuit.business.vo.CategoryQueryVo;
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
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public void save(CategorySaveForm form) {
        Category category = BeanUtil.copyProperties(form, Category.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(category.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            category.setId(CommonUtil.getSnowflakeNextId());
            category.setCreateTime(now);
            category.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            categoryMapper.insert(category);
            log.info("插入标签：{}", category);
        } else { // 修改
            category.setUpdateTime(now);
            categoryMapper.updateByExampleSelective(category, new CategoryExample());
            log.info("修改标签：{}", category);
        }
    }

    @Override
    public PageVo<CategoryQueryVo> queryList(CategoryQueryForm form) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的标签
//        CategoryExample.Criteria criteria = categoryExample.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categorys);
        List<CategoryQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), CategoryQueryVo.class);
        PageVo<CategoryQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询标签列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andIdEqualTo(id);
        categoryMapper.deleteByExample(categoryExample);
    }
}
