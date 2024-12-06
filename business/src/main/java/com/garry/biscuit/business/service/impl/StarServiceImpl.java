package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.garry.biscuit.business.domain.Product;
import com.garry.biscuit.business.domain.ProductExample;
import com.garry.biscuit.business.domain.Star;
import com.garry.biscuit.business.domain.StarExample;
import com.garry.biscuit.business.form.StarQueryForm;
import com.garry.biscuit.business.form.StarSaveForm;
import com.garry.biscuit.business.form.StarStarsForm;
import com.garry.biscuit.business.mapper.ProductMapper;
import com.garry.biscuit.business.mapper.StarMapper;
import com.garry.biscuit.business.service.StarService;
import com.garry.biscuit.business.vo.StarQueryVo;
import com.garry.biscuit.common.enums.ResponseEnum;
import com.garry.biscuit.common.exception.BusinessException;
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
public class StarServiceImpl implements StarService {
    @Resource
    private StarMapper starMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    public void save(StarSaveForm form) {
        Star star = BeanUtil.copyProperties(form, Star.class);
        DateTime now = DateTime.now();

        if (ObjectUtil.isNull(star.getId())) { // 插入
            // 插入时要看数据库有没有唯一键约束，在此校验唯一键约束，防止出现 DuplicationKeyException
            // 对Id、createTime、updateTime 重新赋值
            star.setId(CommonUtil.getSnowflakeNextId());
            star.setCreateTime(now);
            star.setUpdateTime(now);
            // 可能还需要重新赋值其它的字段
            starMapper.insert(star);
            log.info("插入收藏：{}", star);
        } else { // 修改
            star.setUpdateTime(now);
            starMapper.updateByExampleSelective(star, new StarExample());
            log.info("修改收藏：{}", star);
        }
    }

    @Override
    public PageVo<StarQueryVo> queryList(StarQueryForm form) {
        StarExample starExample = new StarExample();
        starExample.setOrderByClause("update_time desc"); // 最新更新的数据，最先被查出来
        // 这里自定义一些过滤的条件，比如:
//        // 用户只能查自己 userId 下的收藏
//        StarExample.Criteria criteria = starExample.createCriteria();
//        if (ObjectUtil.isNotNull(form.getUserId()) {
//            criteria.andUserIdEqualTo(form.getUserId());
//        }
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Star> stars = starMapper.selectByExample(starExample);
        PageInfo<Star> pageInfo = new PageInfo<>(stars);
        List<StarQueryVo> voList = BeanUtil.copyToList(pageInfo.getList(), StarQueryVo.class);
        PageVo<StarQueryVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        vo.setList(voList);
        vo.setMsg("查询收藏列表成功");
        return vo;
    }

    @Override
    public void delete(Long id) {
        StarExample starExample = new StarExample();
        starExample.createCriteria().andIdEqualTo(id);
        starMapper.deleteByExample(starExample);
    }

    @Override
    public void star(Long userId, Long productId) {
        // 查看是不是已经收藏
        StarExample starExample = new StarExample();
        starExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andProductIdEqualTo(productId);
        List<Star> stars = starMapper.selectByExample(starExample);
        if (!stars.isEmpty()) {
            throw new BusinessException(ResponseEnum.BUSINESS_STAR_ALREADY_EXIST);
        }
        // 插入收藏
        StarSaveForm starSaveForm = new StarSaveForm();
        starSaveForm.setUserId(userId);
        starSaveForm.setProductId(productId);
        save(starSaveForm);
    }

    @Override
    public void cancelStar(Long userId, Long productId) {
        // 查看是不是已经收藏
        StarExample starExample = new StarExample();
        starExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andProductIdEqualTo(productId);
        List<Star> stars = starMapper.selectByExample(starExample);
        if (stars.isEmpty()) {
            throw new BusinessException(ResponseEnum.BUSINESS_STAR_NOT_EXIST);
        }
        // 删除收藏
        delete(stars.get(0).getId());
    }

    @Override
    public Integer countStar(Long userId) {
        StarExample starExample = new StarExample();
        starExample.createCriteria().andUserIdEqualTo(userId);
        long count = starMapper.countByExample(starExample);
        return (int) count;
    }

    @Override
    public PageVo<Product> stars(StarStarsForm form) {
        StarExample starExample = new StarExample();
        starExample.createCriteria().andUserIdEqualTo(form.getUserId());
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Star> stars = starMapper.selectByExample(starExample);
        List<Long> productIds = stars.stream()
                .map(Star::getProductId)
                .toList();
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andIdIn(productIds);
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Product> products = productMapper.selectByExample(productExample);
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        PageVo<Product> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        return vo;
    }
}
