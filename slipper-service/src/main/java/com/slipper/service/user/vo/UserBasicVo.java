package com.slipper.service.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改用户基础信息Vo
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
public class UserBasicVo {

    private Long id;
    /**
     * 昵称
     */
    @NotBlank(message = "用户名不能为空")
    private String nickname;
    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空")
    private String avatar;
    /**
     * 性别：0-女 1-男 2-保密
     */
    @NotNull(message = "性别不能为空")
    private Integer sex;
    /**
     * 手机
     */
    private String mobile;
}
