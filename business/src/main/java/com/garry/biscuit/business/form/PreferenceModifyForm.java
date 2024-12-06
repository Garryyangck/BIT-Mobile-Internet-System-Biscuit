package com.garry.biscuit.business.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Garry
 * 2024-12-06 16:49
 */
@Data
public class PreferenceModifyForm {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标签id
     */
    @NotNull(message = "【标签id】不能为空")
    private Long categoryId;

    /**
     * 修改的值
     */
    @NotNull(message = "【修改的值】不能为空")
    private Integer modifyNumber;
}
