package com.garry.biscuit.common.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageVo<T> extends PageInfo<T> {
    /**
     * 后端额外携带的分页信息
     */
    private String msg;
}
