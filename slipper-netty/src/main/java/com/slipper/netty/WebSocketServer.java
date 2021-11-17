package com.slipper.netty;

import com.slipper.netty.config.NettyConfig;
import com.slipper.netty.handler.WebSocketChildHandler;
import com.slipper.netty.service.NettyService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * webSocket服务器
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Component
@Order(1)
public class WebSocketServer implements CommandLineRunner {
    /**
     * 日志控制器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * Netty配置类
     */
    @Resource
    private NettyConfig nettyConfig;

    /**
     * WebSocketServer端
     */
    @Resource
    private WebSocketServer webSocketServer;

    @Resource
    private NettyService nettyService;

//    /**
//     * 启动 单独开启一个线程
//     * @throws Exception
//     */
//    @PostConstruct
//    public void start() throws Exception {
//        run(nettyConfig.getPort(), nettyConfig.getUrl());
//    }

    /**
     * 启动服务端的方法
     * 推荐的线程数量计算公式：
     * 1. 线程数量 = （线程总时间/瓶颈资源时间） * 瓶颈资源的线程并行数
     * 2. QPS    = 1000/线程总时间 * 线程数
     * @param port         服务器监听的端口号
     * @param webSocketUrl webSocket url
     *
     * @throws Exception exception
     */
    public void run(int port, String webSocketUrl) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketChildHandler(webSocketUrl, nettyService));
            Channel ch = bootstrap.bind(port).sync().channel();
            LOGGER.info("🤝[服务器启动端口]: {}\n", port);
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        run(nettyConfig.getPort(), nettyConfig.getUrl());
    }
}
