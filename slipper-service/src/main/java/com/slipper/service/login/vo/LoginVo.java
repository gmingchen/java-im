package com.slipper.service.login.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVo {
    /**
     *
     */
    private Long id;
    /**
     * 帐号
     */
    @NotBlank(message = "帐号不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
