package com.garry.biscuit.business.form;

import com.garry.biscuit.common.form.PageForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Garry
 * 2024-12-06 13:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageQueryForm extends PageForm {

    /**
     * 聊天id
     */
    private Long conversationId;

    /**
     * 用户角色
     */
    private Integer userRole;
}
