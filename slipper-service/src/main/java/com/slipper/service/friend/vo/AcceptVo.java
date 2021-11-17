package com.slipper.service.friend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加好友VO
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 好友申请ID
     */
    @NotNull(message = "好友申请ID不能为空")
    private Long id;
    /**
     * 用户ID
     */
    @JsonProperty("user_id")
    private Long userId;
    /**
     * 分组ID
     */
    @NotNull(message = "分组ID不能为空")
    @JsonProperty("group_id")
    private Long groupId;
}
