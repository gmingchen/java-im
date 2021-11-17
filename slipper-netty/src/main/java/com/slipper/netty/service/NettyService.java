package com.slipper.netty.service;


import com.slipper.netty.entity.MessageEntity;
import com.slipper.service.friend.vo.AcceptVo;
import com.slipper.service.friend.vo.FriendVo;
import com.slipper.service.message.entity.PrivateMessageEntity;
import com.slipper.service.token.entity.TokenEntity;
import com.slipper.service.user.entity.UserEntity;

/**
 * netty
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public interface NettyService {

    /**
     * 获取token
     * @param token
     * @return
     */
    TokenEntity queryToken(String token);

    /**
     * 通过用户id查询用户信息
     * @param id
     * @return
     */
    UserEntity queryUser(Long id);

    /**
     * 验证是否互为好友
     * @param userId
     * @param friendId
     */
    Boolean validatedFriend(Long userId, Long friendId);

    /**
     * 新增消息
     * @param userId
     * @param messageEntity
     * @return
     */
    PrivateMessageEntity creatPrivateMessage(Long userId, MessageEntity messageEntity);

    /**
     * 添加好友
     * @param friendVo
     */
    void createFriend(FriendVo friendVo);

    /**
     * 接受好友请求
     * @param acceptVo
     */
    void acceptFriend(AcceptVo acceptVo);

    /**
     * 修改状态
     * @param id
     * @param status 0-重新申请添加好友 2-拒绝好友请求
     */
    void updateFriendStatus(Long id, Integer status);

    /**
     * 删除好友
     * @param userId
     * @param friendId
     */
    void deleteFriend(Long userId, Long friendId);

}

