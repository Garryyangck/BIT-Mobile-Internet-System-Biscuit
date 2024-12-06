package com.garry.biscuit.business.feign;

import com.garry.biscuit.common.domain.User;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Garry
 * 2024-12-06 00:45
 */
@FeignClient(value = "user")
public interface UserFeign {

    @GetMapping("/user/user/query-users-by-ids")
    ResponseVo<PageVo<User>> queryUsersByIds(@RequestParam("ids") List<Long> ids, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/user/user/query-user-by-id")
    ResponseVo<User> queryUserById(@RequestParam("id") Long id);
}
