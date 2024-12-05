package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.ProductQueryForm;
import com.garry.biscuit.business.form.ProductSaveForm;
import com.garry.biscuit.business.mapper.ProductMapper;
import com.garry.biscuit.business.domain.Product;
import com.garry.biscuit.business.domain.ProductExample;
import com.garry.biscuit.business.service.ProductService;
import com.garry.biscuit.business.vo.ProductQueryVo;
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
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public void save(ProductSaveForm form) {
        Product product = BeanUtil.copyProperties(form, Product.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(product.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            product.setId(CommonUtil.getSnowflakeNextId());
            product.setCreateTime(now);
            product.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            productMapper.insert(product);
            log.info("插入商品：{}", product);
        } else { // 修改
            product.setUpdateTime(now);
            productMapper.updateByExampleSelective(product, new ProductExample());
            log.info("修改商品：{}", product);
        }
    }

    @Override
    public PageVo<ProductQueryVo> queryList(ProductQueryForm form) {
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的商品
//        ProductExample.Criteria criteria = productExample.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Product> products = productMapper.selectByExample(productExample);
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        List<ProductQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), ProductQueryVo.class);
        PageVo<ProductQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询商品列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andIdEqualTo(id);
        productMapper.deleteByExample(productExample);
    }
}
