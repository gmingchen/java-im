package com.slipper.netty.utils;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * WebSocket用户集
 *
 * @author Loafer
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public class WebSocketUsers {

    /**
     * 用户集
     */
    private static final ConcurrentMap<String, Channel> USERS = PlatformDependent.newConcurrentHashMap();
    /**
     * 日志控制器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketUsers.class);

    private static WebSocketUsers ourInstance = new WebSocketUsers();

    private WebSocketUsers() {
    }

    public static WebSocketUsers getInstance() {
        return ourInstance;
    }

    /**
     * 存储通道
     * @param key     唯一键
     * @param channel 通道
     */
    public static void put(String key, Channel channel) {
        USERS.put(key, channel);
    }

    /**
     * 移除通道
     * @param channel 通道
     * @return 移除结果
     */
    public static boolean remove(Channel channel) {
        String key = null;
        boolean b = USERS.containsValue(channel);
        if (b) {
            Set<Map.Entry<String, Channel>> entries = USERS.entrySet();
            for (Map.Entry<String, Channel> entry : entries) {
                Channel value = entry.getValue();
                if (value.equals(channel)) {
                    key = entry.getKey();
                    break;
                }
            }
        } else {
            return true;
        }
        return remove(key);
    }

    /**
     * 移出通道
     * @param key 键
     */
    public static boolean remove(String key) {
        Channel remove = USERS.remove(key);
        boolean containsValue = USERS.containsValue(remove);
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t🤝[移出结果]: {}\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", containsValue ? "失败" : "成功");
        return containsValue;
    }

    /**
     * 获取在线用户列表
     * @return 返回用户集合
     */
    public static ConcurrentMap<String, Channel> getUSERS() {
        return USERS;
    }

    /**
     * 群发消息
     * @param message 消息内容
     */
    public static void sendMessageToUsers(String message) {
        Collection<Channel> values = USERS.values();
        for (Channel value : values) {
            value.write(new TextWebSocketFrame(message));
            value.flush();
        }
    }

    /**
     * 给某个人发送消息
     * @param userName key
     * @param message  消息
     */
    public static void sendMessageToUser(String userName, String message) {
        Channel channel = USERS.get(userName);
        if (channel != null) {
            channel.write(new TextWebSocketFrame(message));
            channel.flush();
        }
    }
}