package com.slipper.service.friend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slipper.common.validator.group.Create;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 好友DTO
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 备注
     */
    private String remark;
    /**
     * 分组ID
     */
    @JsonProperty("group_id")
    private Long groupId;
    /**
     * 状态：0-请求添加好友 1-通过 2-拒绝
     */
    private Integer status;
    /**
     * 好友ID
     */
    @JsonProperty("user_id")
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别：0-女 1-男 2-保密
     */
    private Integer sex;
}
