package com.garry.biscuit.business.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Garry
 * 2024-12-05 23:46
 */
@Data
public class FollowFollowForm {

    /**
     * 用户id
     */
    @NotNull(message = "【用户id】不能为空")
    private Long fromId;

    /**
     * 被关注者id
     */
    @NotNull(message = "【被关注者id】不能为空")
    private Long toId;

}
