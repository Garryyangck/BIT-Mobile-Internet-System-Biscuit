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
     * 已经继承 pageNum、pageSize，在这下面自定义用于过滤查询结果的字段
     */
}
