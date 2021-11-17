package com.slipper.service.friendGroup.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.slipper.service.friend.dto.FriendDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 好友分组
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
public class FriendGroupDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 分组名称
     */
    private String name;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 是否可删除：0-否 1-是
     */
    @JsonProperty("can_deleted")
    private Integer canDeleted;
    /**
     * 好友列表
     */
    private List<FriendDto> friends;
    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Date createdAt;
}
