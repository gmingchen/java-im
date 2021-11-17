package com.slipper.netty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * @author Loafer
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Message
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessageEntity implements Serializable {
    /**
     * 序列化字段
     */
    private static final long serialVersionUID = 1579258906605843062L;

    /**
     * 接收人
     */
    private String acceptName;

    /**
     * 内容
     */
    private String content;

    /**
     * 方式
     * client -> webClient
     * client -> client
     */
    private String method;
}
