package com.slipper.service.friendGroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slipper.common.exception.RunException;
import com.slipper.common.utils.Constant;
import com.slipper.service.friend.entity.FriendEntity;
import com.slipper.service.friend.service.FriendService;
import com.slipper.service.friendGroup.dao.FriendGroupDao;
import com.slipper.service.friendGroup.dto.FriendGroupDto;
import com.slipper.service.friendGroup.entity.FriendGroupEntity;
import com.slipper.service.friendGroup.service.FriendGroupService;
import com.slipper.service.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("friendGroupService")
public class FriendGroupServiceImpl extends ServiceImpl<FriendGroupDao, FriendGroupEntity> implements FriendGroupService {

    @Resource
    private FriendService friendService;

    @Override
    public List<FriendGroupDto> queryGroupFriend(Long userId) {
        return baseMapper.queryGroupFriend(userId);
    }

    @Override
    public List<FriendGroupEntity> queryList(Long userId) {
        LambdaQueryWrapper<FriendGroupEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendGroupEntity::getUserId, userId)
                .orderByAsc(FriendGroupEntity::getCreatedAt);
        return this.list(wrapper);
    }

    @Override
    public FriendGroupEntity create(FriendGroupEntity friendGroupEntity) {
        friendGroupEntity.setCreatedAt(new Date());
        friendGroupEntity.setCanDeleted(1);
        this.save(friendGroupEntity);
        return friendGroupEntity;
    }

    @Override
    public void delete(Long id) {
        FriendGroupEntity friendGroupEntity = this.getById(id);
        if (friendGroupEntity.getCanDeleted() == 0) {
            throw new RunException(Constant.WARNING_CODE, "默认分组不可以删除!");
        }
        // 分组中存在好友不可以删除
        LambdaQueryWrapper<FriendEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendEntity::getUserId, friendGroupEntity.getUserId())
                .eq(FriendEntity::getGroupId, id);
        Integer count = friendService.count(wrapper);
        if (count > 0) {
            throw new RunException(Constant.WARNING_CODE, "请先删除分组中的好友!");
        }

        this.removeById(id);
    }
}
