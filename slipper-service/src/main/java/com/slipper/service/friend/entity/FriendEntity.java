package com.slipper.service.friend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 好友
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "friend")
public class FriendEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 好友用户ID
     */
    @NotNull(message = "好友ID不能为null")
    @JsonProperty("friend_id")
    private Long friendId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态：0-请求添加好友 1-通过 2-拒绝
     */
    private Integer status;
    /**
     * 分组ID
     */
    @NotNull(message = "分组ID不能为null")
    @JsonProperty("group_id")
    private Long groupId;
    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Date createdAt;
}
