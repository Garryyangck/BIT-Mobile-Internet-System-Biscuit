package com.garry.biscuit.business.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Garry
 * 2024-12-05 23:07
 */
@Data
public class FollowSaveForm {

    /**
     * id
     */
    private Long id;

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

    /**
     * 新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

}
