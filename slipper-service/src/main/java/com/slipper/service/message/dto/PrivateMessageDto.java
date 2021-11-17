package com.slipper.service.message.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.slipper.common.validator.group.Create;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 私聊消息DTO
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
public class PrivateMessageDto {
    private Long id;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送者ID
     */
    private Long from;
    /**
     * 接收者ID
     */
    private Long to;
    /**
     * 消息类型：1-text 2-image 3-file
     */
    private Integer type;
    /**
     * 接收状态 0-未读 1-已读
     */
    private Integer status;
    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Long createdAt;
    /**
     * 资源路径
     */
    private String url;
}
