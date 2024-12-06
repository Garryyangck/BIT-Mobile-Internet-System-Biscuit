package com.garry.biscuit.business.form;

import com.garry.biscuit.common.form.PageForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Garry
 * 2024-12-06 14:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductRecommendForm extends PageForm {

    /**
     * 用户id
     */
    private Long userId;
}
