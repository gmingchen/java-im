package com.slipper.service.friendGroup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 好友分组
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "friend_group")
public class FriendGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空")
    private String name;
    /**
     * 用户ID
     */
    @JsonProperty("user_id")
    private Long userId;
    /**
     * 是否可删除：0-否 1-是
     */
    @JsonProperty("can_deleted")
    private Integer canDeleted;
    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Date createdAt;
}
