package com.garry.biscuit.common.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 作为所有需要分页的 form 请求的父类，统一提供 pageNum 和 pageSize
 */
@Data
public class PageForm {
    /**
     * 查询页码
     */
    @NotNull(message = "【查询页码】不能为空")
    private Integer pageNum; // 不能使用 int，int 有默认值 0，而 Integer 的默认值是 null，int 会“蒙混过关”

    /**
     * 每页条数
     */
    @NotNull(message = "【每页条数】不能为空")
    @Max(value = 100, message = "【每页条数】不能超过上限100")
    private Integer pageSize;
}
