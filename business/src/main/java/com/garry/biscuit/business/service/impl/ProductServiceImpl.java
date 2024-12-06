package com.garry.biscuit.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.garry.biscuit.business.domain.Preference;
import com.garry.biscuit.business.domain.PreferenceExample;
import com.garry.biscuit.business.domain.Product;
import com.garry.biscuit.business.domain.ProductExample;
import com.garry.biscuit.business.enums.ProductStatusEnum;
import com.garry.biscuit.business.feign.UserFeign;
import com.garry.biscuit.business.form.ProductQueryForm;
import com.garry.biscuit.business.form.ProductRecommendForm;
import com.garry.biscuit.business.form.ProductSaveForm;
import com.garry.biscuit.business.form.ProductSearchForm;
import com.garry.biscuit.business.mapper.PreferenceMapper;
import com.garry.biscuit.business.mapper.ProductMapper;
import com.garry.biscuit.business.service.ElasticSearchService;
import com.garry.biscuit.business.service.ProductService;
import com.garry.biscuit.business.vo.ProductDetailVo;
import com.garry.biscuit.business.vo.ProductQueryVo;
import com.garry.biscuit.business.vo.ProductRecommendVo;
import com.garry.biscuit.business.vo.ProductSearchVo;
import com.garry.biscuit.common.domain.User;
import com.garry.biscuit.common.util.CommonUtil;
import com.garry.biscuit.common.util.PageUtil;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Resource
    private PreferenceMapper preferenceMapper;

    @Resource
    private UserFeign userFeign;

    @Resource
    private ElasticSearchService elasticSearchService;

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
        productExample.createCriteria().andStatusEqualTo(ProductStatusEnum.NORMAL.getCode());
        productExample.setOrderByClause("id desc");
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

    @Override
    public PageVo<ProductRecommendVo> recommend(ProductRecommendForm form) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andStatusEqualTo(ProductStatusEnum.NORMAL.getCode());
        productExample.setOrderByClause("id desc");
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Product> products = productMapper.selectByExample(productExample);
        List<ProductRecommendVo> productRecommendVos = BeanUtil.copyToList(products, ProductRecommendVo.class);
        List<ProductRecommendVo> selectedProductRecommendVos = new ArrayList<>();
        // 获取这一页的所有商品的偏好值
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            PreferenceExample preferenceExample = new PreferenceExample();
            preferenceExample.createCriteria()
                    .andUserIdEqualTo(form.getUserId())
                    .andCategoryIdEqualTo(product.getCategoryId());
            List<Preference> preferences = preferenceMapper.selectByExample(preferenceExample);
            // 没有查到，说明还没有添加此偏好，偏好值是 0
            if (preferences.isEmpty()) {
                ProductRecommendVo productRecommendVo = productRecommendVos.get(i);
                productRecommendVo.setPreferenceNumber(0);
                productRecommendVos.set(i, productRecommendVo);
                continue;
            }
            ProductRecommendVo productRecommendVo = productRecommendVos.get(i);
            productRecommendVo.setPreferenceNumber(preferences.get(0).getNumber());
            productRecommendVos.set(i, productRecommendVo);
        }
        // 根据偏好值降序排列
        productRecommendVos.sort(Comparator.comparing(ProductRecommendVo::getPreferenceNumber).reversed());
        productRecommendVos = productRecommendVos.subList(0, productRecommendVos.size() / 2);
        // 获取卖家的信息
        productRecommendVos = productRecommendVos.stream()
                .peek(productRecommendVo -> {
                    ResponseVo<User> userResponseVo = userFeign.queryUserById(productRecommendVo.getSellerId());
                    User seller = userResponseVo.getData();
                    productRecommendVo.setSellerName(seller.getUserName());
                    productRecommendVo.setSellerAvatar(seller.getUserAvatar());
                    productRecommendVo.setSellerProfile(seller.getUserProfile());
                    productRecommendVo.setSellerSignature(seller.getUserSignature());
                    productRecommendVo.setSellerExperience(seller.getUserExperience());
                    productRecommendVo.setSellerLevel(seller.getUserLevel());
                    productRecommendVo.setSellerRole(seller.getUserRole());
                }).toList();
        PageInfo<ProductRecommendVo> pageInfo = PageUtil.getPageInfo(productRecommendVos, form.getPageNum(), form.getPageSize());
        PageVo<ProductRecommendVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        return vo;
    }

    @Override
    public ProductDetailVo detail(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        ProductDetailVo vo = BeanUtil.copyProperties(product, ProductDetailVo.class);
        User user = userFeign.queryUserById(vo.getSellerId()).getData();
        vo.setSellerName(user.getUserName());
        vo.setSellerAvatar(user.getUserAvatar());
        vo.setSellerProfile(user.getUserProfile());
        vo.setSellerSignature(user.getUserSignature());
        vo.setSellerExperience(user.getUserExperience());
        vo.setSellerLevel(user.getUserLevel());
        vo.setSellerRole(user.getUserRole());
        return vo;
    }

    @Override
    public PageVo<ProductSearchVo> search(ProductSearchForm form) {
        // 根据关键词搜索商品
        List<Product> products = elasticSearchService.searchProduct(form.getContent());

        // 过滤、排序
        products = products.stream()
                .filter(product -> {
                    // 价格范围过滤
                    boolean priceFilter = true;
                    BigDecimal priceMin = form.getPriceMin();
                    BigDecimal priceMax = form.getPriceMax();
                    if (priceMin != null && priceMax != null) {
                        priceFilter = (product.getPrice().compareTo(priceMin) >= 0) && (product.getPrice().compareTo(priceMax) <= 0);
                    } else if (priceMin != null) {
                        priceFilter = product.getPrice().compareTo(priceMin) >= 0;
                    } else if (priceMax != null) {
                        priceFilter = product.getPrice().compareTo(priceMax) <= 0;
                    }
                    // 地点过滤
                    boolean locationFilter = form.getLocation() != null && product.getLocation().equals(form.getLocation());
                    return priceFilter && locationFilter;
                }).sorted((o1, o2) -> {
                    Integer productSearchSort = form.getProductSearchSort();
                    return switch (productSearchSort) {
                        case 0 ->
                            // 不排序
                                0;
                        case 1 ->
                            // 价格升序
                                o1.getPrice().compareTo(o2.getPrice());
                        case 2 ->
                            // 价格降序
                                o2.getPrice().compareTo(o1.getPrice());
                        case 3 ->
                            // 成色升序
                                o1.getFineness() - o2.getFineness();
                        case 4 ->
                            // 成色降序
                                o2.getFineness() - o1.getFineness();
                        default -> 0;
                    };
                }).toList();

        // 创建 ProductSearchVo
        List<ProductSearchVo> productSearchVos = BeanUtil.copyToList(products, ProductSearchVo.class);
        productSearchVos = productSearchVos.stream()
                .peek(productSearchVo -> {
                    User user = userFeign.queryUserById(productSearchVo.getSellerId()).getData();
                    productSearchVo.setSellerName(user.getUserName());
                    productSearchVo.setSellerAvatar(user.getUserAvatar());
                    productSearchVo.setSellerProfile(user.getUserProfile());
                    productSearchVo.setSellerSignature(user.getUserSignature());
                    productSearchVo.setSellerExperience(user.getUserExperience());
                    productSearchVo.setSellerLevel(user.getUserLevel());
                    productSearchVo.setSellerRole(user.getUserRole());
                })
                .toList();

        PageInfo<ProductSearchVo> pageInfo = PageUtil.getPageInfo(productSearchVos, form.getPageNum(), form.getPageSize());
        PageVo<ProductSearchVo> vo = BeanUtil.copyProperties(pageInfo, PageVo.class);
        return vo;
    }
}
