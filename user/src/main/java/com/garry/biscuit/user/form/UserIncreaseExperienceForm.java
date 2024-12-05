package com.garry.biscuit.user.form;

import com.garry.biscuit.user.enums.UserExperienceIncreaseEnum;
import lombok.Data;

/**
 * @author Garry
 * 2024-12-05 22:07
 */
@Data
public class UserIncreaseExperienceForm {

    /**
     * id
     */
    private Long id;

    /**
     * 增加经验值的手段
     */
    private UserExperienceIncreaseEnum experienceIncreaseEnum;

}
