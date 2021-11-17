package com.slipper.netty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.slipper.netty.entity.MessageEntity;
import com.slipper.netty.entity.ResponseBodyEntity;
import com.slipper.netty.entity.ResponseEntity;
import com.slipper.netty.service.NettyService;
import com.slipper.netty.utils.WebSocketUsers;
import com.slipper.service.conversation.entity.ConversationEntity;
import com.slipper.service.conversation.service.ConversationService;
import com.slipper.service.friend.entity.FriendEntity;
import com.slipper.service.friend.service.FriendService;
import com.slipper.service.friend.vo.AcceptVo;
import com.slipper.service.friend.vo.FriendVo;
import com.slipper.service.message.entity.PrivateMessageEntity;
import com.slipper.service.message.service.PrivateMessageService;
import com.slipper.service.token.entity.TokenEntity;
import com.slipper.service.token.service.TokenService;
import com.slipper.service.user.entity.UserEntity;
import com.slipper.service.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * netty
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Service("nettyService")
public class NettyServiceImpl implements NettyService {

    @Resource
    private TokenService tokenService;
    @Resource
    private UserService userService;
    @Resource
    private FriendService friendService;
    @Resource
    private PrivateMessageService privateMessageService;
    @Resource
    private ConversationService conversationService;

    @Override
    public TokenEntity queryToken(String token) {
        return tokenService.queryByToken(token);
    }

    @Override
    public UserEntity queryUser(Long id) {
        return userService.queryBasicById(id);
    }

    @Override
    public Boolean validatedFriend(Long userId, Long friendId) {
        return friendService.validatedFriend(userId, friendId);
    }

    @Override
    public PrivateMessageEntity creatPrivateMessage(Long userId, MessageEntity messageEntity) {
        Date now = new Date();
        PrivateMessageEntity privateMessageEntity = new PrivateMessageEntity();
        privateMessageEntity.setFrom(userId);
        privateMessageEntity.setTo(messageEntity.getTo());
        privateMessageEntity.setType(messageEntity.getMessageType());
        privateMessageEntity.setContent(messageEntity.getContent());
        privateMessageEntity.setUrl(messageEntity.getUrl());
        privateMessageEntity.setCreatedAt(now.getTime());
        privateMessageService.save(privateMessageEntity);
        privateMessageEntity.setAck(messageEntity.getAck());

        // 判断好友是否有聊天会话没有则创建
        LambdaQueryWrapper<ConversationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ConversationEntity::getFrom, messageEntity.getTo())
                .eq(ConversationEntity::getTo, userId);
        Integer count = conversationService.count(wrapper);
        if (count == 0) {
            ConversationEntity conversationEntity = new ConversationEntity();
            conversationEntity.setFrom(messageEntity.getTo());
            conversationEntity.setTo(userId);
            conversationEntity.setType(1);
            conversationEntity.setCreatedAt(now);
            conversationService.save(conversationEntity);
        }

        return privateMessageEntity;
    }

    @Override
    public void createFriend(FriendVo friendVo) {
        friendService.create(friendVo);

        ResponseBodyEntity responseBodyEntity = new ResponseBodyEntity();
        responseBodyEntity.setUser(userService.queryBasicById(friendVo.getUserId()));

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setType(4);
        responseEntity.setResponseBody(responseBodyEntity);

        Gson gson = new Gson();
        WebSocketUsers.sendMessageToUser(friendVo.getUsername(), gson.toJson(responseEntity));
    }

    @Override
    public void acceptFriend(AcceptVo acceptVo) {
        friendService.accept(acceptVo);

        FriendEntity friendEntity = friendService.getById(acceptVo.getId());
        // 同意的用户
        UserEntity userEntity = userService.queryBasicById(friendEntity.getFriendId());
        // 被同意的用户
        UserEntity friend = userService.queryBasicById(friendEntity.getUserId());

        ResponseBodyEntity responseBodyEntity = new ResponseBodyEntity();
        responseBodyEntity.setUser(userEntity);

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setType(5);
        responseEntity.setResponseBody(responseBodyEntity);

        Gson gson = new Gson();
        WebSocketUsers.sendMessageToUser(friend.getUsername(), gson.toJson(responseEntity));
    }

    @Override
    public void updateFriendStatus(Long id, Integer status) {
        friendService.updateStatus(id, status);

        FriendEntity friendEntity = friendService.getById(id);
        // 同意的用户
        UserEntity userEntity = userService.queryBasicById(friendEntity.getFriendId());
        // 被同意的用户
        UserEntity friend = userService.queryBasicById(friendEntity.getUserId());

        ResponseBodyEntity responseBodyEntity = new ResponseBodyEntity();
        ResponseEntity responseEntity = new ResponseEntity();
        Gson gson = new Gson();

        if (status == 2) { // 拒绝好友请求
            responseEntity.setType(6);
            responseBodyEntity.setUser(userEntity);
            responseEntity.setResponseBody(responseBodyEntity);
            WebSocketUsers.sendMessageToUser(friend.getUsername(), gson.toJson(responseEntity));
        } else if (status == 0) { // 重新申请添加好友
            responseEntity.setType(4);
            responseBodyEntity.setUser(friend);
            responseEntity.setResponseBody(responseBodyEntity);
            WebSocketUsers.sendMessageToUser(userEntity.getUsername(), gson.toJson(responseEntity));
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        friendService.delete(userId, friendId);

        UserEntity userEntity = userService.queryBasicById(userId);
        UserEntity friendEntity = userService.queryBasicById(friendId);

        ResponseBodyEntity responseBodyEntity = new ResponseBodyEntity();
        responseBodyEntity.setUser(userEntity);

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setType(7);
        responseEntity.setResponseBody(responseBodyEntity);

        Gson gson = new Gson();
        WebSocketUsers.sendMessageToUser(friendEntity.getUsername(), gson.toJson(responseEntity));


    }
}