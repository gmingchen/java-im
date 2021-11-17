package com.slipper.service.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.slipper.common.validator.group.Create;
import com.slipper.common.validator.group.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {Create.class})
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {Create.class})
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 性别：0-女 1-男 2-保密
     */
    private Integer sex;
    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Date createdAt;
}
