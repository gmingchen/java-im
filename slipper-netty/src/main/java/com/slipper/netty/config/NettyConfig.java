package com.slipper.netty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Netty配置
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@ConfigurationProperties(prefix = "netty")
@Component
public class NettyConfig {

    /**
     * WebSocket-netty Server port
     */
    private int port;

    /**
     * WebSocket Url
     */
    private String url;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
