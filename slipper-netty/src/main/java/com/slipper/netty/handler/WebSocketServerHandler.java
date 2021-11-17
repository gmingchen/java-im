package com.slipper.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.slipper.common.utils.R;
import com.slipper.common.validator.ValidatorUtils;
import com.slipper.netty.entity.*;
import com.slipper.netty.service.NettyService;
import com.slipper.netty.utils.WebSocketUsers;
import com.slipper.service.message.entity.PrivateMessageEntity;
import com.slipper.service.token.entity.TokenEntity;
import com.slipper.service.user.entity.UserEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * WebSocketServer端处理器
 *
 * @author Loafer
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * 日志控制器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerHandler.class);
    /**
     * 管道附属key
     */
    private static final AttributeKey<UserEntity> ATTRIBUTE_KEY = AttributeKey.valueOf("user");

    /**
     * webSocketUrl
     */
    private String webSocketUrl;

    /**
     * 用于打开关闭握手
     */
    private WebSocketServerHandshaker socketServerHandShaker;

    private NettyService nettyService;

    public WebSocketServerHandler(String webSocketUrl, NettyService nettyService) {
        this.webSocketUrl = webSocketUrl;
        this.nettyService = nettyService;
    }

    /**
     * 异常
     * @param channelHandlerContext channelHandlerContext
     * @param cause                 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        cause.printStackTrace();
        LOGGER.error("🤝[服务端捕捉异常]: {}", cause.getMessage());
        channelHandlerContext.close();
    }

    /**
     * 当客户端主动链接服务端的链接后，调用此方法
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        LOGGER.info("🤝与客户端[{}]建立连接\n" +
                        "🤝[服务器当前在线人数]: {}"
                , channelHandlerContext.channel().remoteAddress()
                , WebSocketUsers.getUSERS().size() + 1);
    }

    /**
     * 与客户端断开连接时 调用此方法
     * @param channelHandlerContext channelHandlerContext
     */
    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        LOGGER.info("🤝与客户端[{}]断开连接", channel.remoteAddress());
        // 从存储结构中移除通道，也可以用缓存来替代
        WebSocketUsers.remove(channel);
        // 关闭通道
        channel.close();
    }

    /**
     * 读完之后调用的方法
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    /**
     * @param channelHandlerContext
     * @param o
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        messageReceived(channelHandlerContext, o);
    }

    /**
     * 接收客户端发送的消息
     * @param channelHandlerContext ChannelHandlerContext
     * @param receiveMessage        消息
     */
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object receiveMessage) throws Exception {
        // 传统http接入 第一次需要使用http建立握手
        if (receiveMessage instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) receiveMessage;
            LOGGER.info("🤝[握手]: {}", fullHttpRequest.uri());
            // 握手
            handlerHttpRequest(channelHandlerContext, fullHttpRequest);
            // 发送连接成功给客户端
            Gson gson = new Gson();
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setType(0);
            responseEntity.setMessage("success");
            String responseString = gson.toJson(responseEntity);
            channelHandlerContext.channel().write(new TextWebSocketFrame(responseString));
        }
        // WebSocket接入
        else if (receiveMessage instanceof WebSocketFrame) {
            WebSocketFrame webSocketFrame = (WebSocketFrame) receiveMessage;
            handlerWebSocketFrame(channelHandlerContext, webSocketFrame);
        }
    }

    /**
     * 第一次握手 判断用户是否存在 验证token 是否过期
     * @param channelHandlerContext channelHandlerContext
     * @param req                   请求
     */
    private void handlerHttpRequest(ChannelHandlerContext channelHandlerContext, FullHttpRequest req) {
        // 构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory
                = new WebSocketServerHandshakerFactory(webSocketUrl, null, false);
        // 从连接路径中截取连接用户名
        String uri = req.uri();
        int i = uri.lastIndexOf("/");
        String token = uri.substring(i + 1, uri.length());
        TokenEntity tokenEntity = nettyService.queryToken(token);
        // 判断凭证是否过期
        boolean bool = tokenEntity != null && tokenEntity.getExpiredAt().getTime() >= new Date().getTime();
        if(bool) {
            UserEntity userEntity = nettyService.queryUser(tokenEntity.getUserId());
            Channel connectChannel = channelHandlerContext.channel();
            // 加入在线用户
            WebSocketUsers.put(userEntity.getUsername(), connectChannel);
            // 管道绑定用户信息
            connectChannel.attr(ATTRIBUTE_KEY).set(userEntity);
            socketServerHandShaker = wsFactory.newHandshaker(req);
            if (socketServerHandShaker == null) {
                // 发送版本错误
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(connectChannel);
            } else {
                // 握手响应
                socketServerHandShaker.handshake(connectChannel, req);
            }
        } else {
            // 断开连接
            channelHandlerContext.close();
        }
    }

    /**
     * webSocket处理逻辑
     * @param channelHandlerContext channelHandlerContext
     * @param frame                 webSocketFrame
     */
    private void handlerWebSocketFrame(ChannelHandlerContext channelHandlerContext, WebSocketFrame frame) throws IOException {
        Channel channel = channelHandlerContext.channel();
        // region 纯文本消息
        if (frame instanceof TextWebSocketFrame) {
            String requestString = ((TextWebSocketFrame) frame).text();
            requestProcessing(requestString, channel);
            return;
        }
        // region 二进制消息
        if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) frame;
            ByteBuf content = binaryWebSocketFrame.content();
            LOGGER.info("🤝[二进制数据]:{}", content);
            final int length = content.readableBytes();
            final byte[] array = new byte[length];
            content.getBytes(content.readerIndex(), array, 0, length);
            MessagePack messagePack = new MessagePack();
            WebSocketMessageEntity webSocketMessageEntity = messagePack.read(array, WebSocketMessageEntity.class);
            LOGGER.info("🤝[解码数据]: {}", webSocketMessageEntity);
            WebSocketUsers.sendMessageToUser(webSocketMessageEntity.getAcceptName(), webSocketMessageEntity.getContent());
        }
    }

    /**
     * 请求处理
     */
    private void requestProcessing(String requestString, Channel channel) {
        UserEntity fromUser = channel.attr(ATTRIBUTE_KEY).get();
        Gson gson = new Gson();
        RequestEntity request = gson.fromJson(requestString, RequestEntity.class);
        ResponseEntity responseEntity = new ResponseEntity();
        String responseString;
        switch (request.getType()) {
            case 0: // 心跳
                responseEntity.setType(0);
                responseString = gson.toJson(responseEntity);
                channel.write(new TextWebSocketFrame(responseString));
                break;
            case 1: // 通讯消息
                // 参数校验
                String requestValidatorMessage = ValidatorUtils.validated(request);
                if (StringUtils.isNoneBlank(requestValidatorMessage)) {
                    responseEntity.setType(3);
                    responseEntity.setMessage(requestValidatorMessage);
                    responseString = gson.toJson(responseEntity);
                    channel.write(new TextWebSocketFrame(responseString));
                    return;
                }
                MessageEntity messageEntity = request.getRequestBody().getMessage();;
                UserEntity toUser = nettyService.queryUser(request.getRequestBody().getMessage().getTo());
                if (messageEntity.getType() == 1) { // 私聊
                    Long toUserId = request.getRequestBody().getMessage().getTo();
                    // 验证是否互为好友
                    boolean isFriend = nettyService.validatedFriend(fromUser.getId(), toUserId);
                    if (!isFriend) {
                        responseEntity.setType(3);
                        responseEntity.setMessage("对方不是您的好友!");
                        responseString = gson.toJson(responseEntity);
                        channel.write(new TextWebSocketFrame(responseString));
                        return;
                    } else {
                        // 消息插入数据库中
                        PrivateMessageEntity privateMessageEntity = nettyService.creatPrivateMessage(fromUser.getId(),messageEntity);
                        // 设置响应体
                        ResponseBodyEntity responseBodyEntity = new ResponseBodyEntity();
                        responseBodyEntity.setMessage(privateMessageEntity);
                        // 发送给接收者
                        responseEntity.setResponseBody(responseBodyEntity);
                        responseEntity.setType(1);
                        WebSocketUsers.sendMessageToUser(toUser.getUsername(), gson.toJson(responseEntity));
                        // 发送给发送者 确认消息
                        ResponseEntity ackResponse = new ResponseEntity();
                        ackResponse.setResponseBody(responseBodyEntity);
                        ackResponse.setType(2);
                        WebSocketUsers.sendMessageToUser(fromUser.getUsername(), gson.toJson(ackResponse));
                    }
                }
                break;
        }
    }
}
