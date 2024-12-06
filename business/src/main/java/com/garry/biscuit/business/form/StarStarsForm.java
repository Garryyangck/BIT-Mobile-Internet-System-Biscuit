package com.garry.biscuit.business.form;

import com.garry.biscuit.common.form.PageForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Garry
 * 2024-12-06 10:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StarStarsForm extends PageForm {

    /**
     * 用户id
     */
    private Long userId;
}
