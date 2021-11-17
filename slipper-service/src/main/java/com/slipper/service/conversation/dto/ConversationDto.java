package com.slipper.service.conversation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slipper.service.message.dto.PrivateMessageDto;
import com.slipper.service.user.dto.UserBasicDto;
import lombok.Data;

import java.util.Date;

/**
 * 聊天会话DTO
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
public class ConversationDto {

    private Long id;
    /**
     * 发送者
     */
    private UserBasicDto from;
    /**
     * 接收者
     */
    private UserBasicDto to;;
    /**
     * 消息
     */
    private PrivateMessageDto message;
    /**
     * 类型：1-私聊 2-群聊
     */
    private Integer type;
    /**
     * 未读数量
     */
    @JsonProperty("unread_count")
    private Integer unreadCount;
    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Date createdAt;
}
