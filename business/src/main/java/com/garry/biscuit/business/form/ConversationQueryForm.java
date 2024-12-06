package com.garry.biscuit.business.form;

import com.garry.biscuit.common.form.PageForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConversationQueryForm extends PageForm {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户角色
     */
    private Integer userRole;
}
