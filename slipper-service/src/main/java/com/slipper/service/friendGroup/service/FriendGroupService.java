package com.slipper.service.friendGroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.slipper.service.friendGroup.dto.FriendGroupDto;
import com.slipper.service.friendGroup.entity.FriendGroupEntity;

import java.util.List;

/**
 * 好友分组
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public interface FriendGroupService extends IService<FriendGroupEntity> {
    /**
     * 用户id查询分组 及好友
     * @return
     */
    List<FriendGroupDto> queryGroupFriend(Long userId);

    /**
     * 用户id查询好友分组列表
     * @param userId
     * @return
     */
    List<FriendGroupEntity> queryList(Long userId);

    /**
     * 创建分组
     * @param friendGroupEntity
     */
    FriendGroupEntity create(FriendGroupEntity friendGroupEntity);

    /**
     * 删除分组
     * @param id
     */
    void delete(Long id);
}
