package com.garry.biscuit.business.service;

import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.business.form.PreferenceQueryForm;
import com.garry.biscuit.business.form.PreferenceSaveForm;
import com.garry.biscuit.business.vo.PreferenceQueryVo;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
public interface PreferenceService {
    /**
     * 插入新偏好，或修改已有的偏好
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(PreferenceSaveForm form);

    /**
     * 查询所有的偏好，支持分页
     */
    PageVo<PreferenceQueryVo> queryList(PreferenceQueryForm form);

    /**
     * 根据 id 删除偏好
     */
    void delete(Long id);
}
