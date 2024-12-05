package com.garry.biscuit.business.service;

import com.garry.biscuit.business.form.*;
import com.garry.biscuit.business.vo.FollowQueryVo;
import com.garry.biscuit.common.vo.PageVo;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
public interface FollowService {
    /**
     * 插入新关注，或修改已有的关注
     * 如果 form.id = null，则为插入；
     * 如果 form.id != null，则为修改
     */
    void save(FollowSaveForm form);

    /**
     * 查询所有的关注，支持分页
     */
    PageVo<FollowQueryVo> queryList(FollowQueryForm form);

    /**
     * 根据 id 删除关注
     */
    void delete(Long id);

    /**
     * 关注
     */
    void follow(Long fromId, Long toId);

    /**
     * 查询粉丝数
     */
    Integer countFollower(Long toId);

    /**
     * 查询关注数
     */
    Integer countFollowee(Long fromId);

    /**
     * 取消关注
     */
    void cancelFollow(Long fromId, Long toId);
}
