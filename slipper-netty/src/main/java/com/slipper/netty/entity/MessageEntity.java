package com.slipper.netty.entity;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class MessageEntity implements Serializable {
    /**
     * 序列化字段
     */
    private static final long serialVersionUID = 1L;
    /**
     * 消息确认字段
     */
    @NotBlank(message = "ack不能为空")
    private String ack;
    /**
     * 接收者
     */
    @NotNull(message = "接受者不能为null")
    private Long to;
    /**
     * 接收类型 1-私聊 2-群聊
     */
    @Min(value = 1, message = "接收类型不正确")
    @Max(value = 2, message = "接收类型不正确")
    private Integer type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息类型：1-text 2-image 3-video
     */
    @Min(value = 1, message = "消息类型不正确")
    @Max(value = 3, message = "消息类型不正确")
    private Integer messageType;
    /**
     * 资源路径
     */
    private String url;
}
