package com.slipper.service.friend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slipper.service.user.dto.UserBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 好友申请DTO
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDto implements Serializable {
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
     * 审核者
     */
    private UserBasicDto reviewer;
    /**
     * 申请者
     */
    private UserBasicDto applicant;
}
