package com.garry.biscuit.business.form;

import com.garry.biscuit.common.form.PageForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Garry
 * 2024-12-06 01:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FollowFolloweesForm extends PageForm {

    /**
     * 用户id
     */
    private Long fromId;
}
