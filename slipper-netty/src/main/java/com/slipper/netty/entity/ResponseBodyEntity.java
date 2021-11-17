package com.slipper.netty.entity;

import com.slipper.service.message.entity.PrivateMessageEntity;
import com.slipper.service.user.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseBodyEntity implements Serializable {
    /**
     * 序列化字段
     */
    private static final long serialVersionUID = 1L;
    /**
     * 发送者
     */
    private UserEntity from;
    /**
     * 消息
     */
    private PrivateMessageEntity message;
    /**
     * 消息
     */
    private UserEntity user;
}
