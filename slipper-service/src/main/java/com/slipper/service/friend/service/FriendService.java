package com.slipper.service.friend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.slipper.service.friend.dto.ApplyDto;
import com.slipper.service.friend.dto.FriendDto;
import com.slipper.service.friend.entity.FriendEntity;
import com.slipper.service.friend.vo.AcceptVo;
import com.slipper.service.friend.vo.FriendVo;

import java.util.List;

/**
 * 好友
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public interface FriendService extends IService<FriendEntity> {

    /**
     * 分组id查询好友列表
     * @param groupId
     * @return
     */
    List<FriendDto> queryFriendByGroupId(Long groupId);

    /**
     * 添加好友
     * @param friendVo
     */
    void create(FriendVo friendVo);

    /**
     * 接受好友请求
     * @param acceptVo
     */
    void accept(AcceptVo acceptVo);

    /**
     * 修改状态
     * @param id
     * @param status 0-重新申请添加好友 2-拒绝好友请求
     */
    void updateStatus(Long id, Integer status);

    /**
     * 移动好友
     * @param acceptVo
     */
    void move(AcceptVo acceptVo);

    /**
     * 删除好友
     * @param userId
     * @param friendId
     */
    void delete(Long userId, Long friendId);

    /**
     * 查询好友申请列表
     * @param userId
     * @return
     */
    List<ApplyDto> queryFriendApply(Long userId);

    /**
     * 验证是否互为好友
     * @param userId
     * @param friendId
     */
    Boolean validatedFriend(Long userId, Long friendId);
}
