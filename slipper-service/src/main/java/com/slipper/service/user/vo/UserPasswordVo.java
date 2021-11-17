package com.slipper.service.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改用户基础信息Vo
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
public class UserPasswordVo {

    private Long id;
    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空")
    @JsonProperty("old_password")
    private String oldPassword;
    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    @JsonProperty("new_password")
    private String newPassword;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @JsonProperty("confirm_password")
    private String confirmPassword;
}
