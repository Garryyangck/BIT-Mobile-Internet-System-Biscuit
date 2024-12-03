package com.garry.biscuit.${module}.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.${module}.form.${Domain}QueryForm;
import com.garry.biscuit.${module}.form.${Domain}SaveForm;
import com.garry.biscuit.${module}.vo.${Domain}QueryVo;

/**
 * @author Garry
 * ${DateTime}
 */
public interface ${Domain}Service {
    /**
     * 插入新${tableNameCn}，或修改已有的${tableNameCn}
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(${Domain}SaveForm form);

    /**
     * 查询所有的${tableNameCn}，支持分页
     */
    PageVo<${Domain}QueryVo> queryList(${Domain}QueryForm form);

    /**
     * 根据 id 删除${tableNameCn}
     */
    void delete(Long id);
}
