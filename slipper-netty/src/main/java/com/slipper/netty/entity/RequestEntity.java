package com.slipper.netty.entity;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

@Data
public class RequestEntity implements Serializable {
    /**
     * 序列化字段
     */
    private static final long serialVersionUID = 1L;
    /**
     * 请求类型 0-心跳 1-发送消息
     */
    private Integer type;
    /**
     * 请求主要内容
     */
    @Valid
    private RequestBodyEntity requestBody;
}
