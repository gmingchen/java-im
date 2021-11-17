package com.slipper.netty.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RequestBodyEntity implements Serializable {
    /**
     * 序列化字段
     */
    private static final long serialVersionUID = 1L;
    /**
     * 消息
     */
    @Valid
    private MessageEntity message;

}
