package com.garry.biscuit.business.form;

import com.garry.biscuit.common.form.PageForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author Garry
 * 2024-12-06 15:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductSearchForm extends PageForm {

    /**
     * 搜索内容
     */
    @NotBlank(message = "【搜索内容】不能为空")
    private String content;

    /**
     * 排序的方式，[ProductSearchSortEnum]
     */
    @NotNull(message = "【排序的方式】不能为空")
    private Integer productSearchSort;

    /**
     * 价格最小值
     */
    private BigDecimal priceMin;

    /**
     * 价格最大值
     */
    private BigDecimal priceMax;

    /**
     * 位置
     */
    private Integer location;
}
