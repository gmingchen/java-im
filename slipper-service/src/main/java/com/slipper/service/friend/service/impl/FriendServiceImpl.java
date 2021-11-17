package com.slipper.service.friend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slipper.common.exception.RunException;
import com.slipper.common.utils.Constant;
import com.slipper.service.conversation.entity.ConversationEntity;
import com.slipper.service.conversation.service.ConversationService;
import com.slipper.service.friend.dao.FriendDao;
import com.slipper.service.friend.dto.ApplyDto;
import com.slipper.service.friend.dto.FriendDto;
import com.slipper.service.friend.entity.FriendEntity;
import com.slipper.service.friend.service.FriendService;
import com.slipper.service.friend.vo.AcceptVo;
import com.slipper.service.friend.vo.FriendVo;
import com.slipper.service.user.entity.UserEntity;
import com.slipper.service.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 好友
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Service("friendService")
public class FriendServiceImpl extends ServiceImpl<FriendDao, FriendEntity> implements FriendService {

    @Resource
    private UserService userService;

    @Resource
    private ConversationService conversationService;

    @Override
    public List<FriendDto> queryFriendByGroupId(Long groupId) {
        return baseMapper.queryFriendByGroupId(groupId);
    }

    @Override
    public void create(FriendVo friendVo) {
        // 查询添加的好友账户是否存在
        UserEntity userEntity = userService.queryBasicByUsername(friendVo.getUsername());
        if (userEntity == null) {
            throw new RunException(Constant.WARNING_CODE, "该用户不存在!");
        }
        // 验证是否添加自己为好友
        if (friendVo.getUserId() == userEntity.getId()) {
            throw new RunException(Constant.WARNING_CODE, "不可以添加自己为好友!");
        }
        // 验证是否互为好友
        Boolean bool = validatedFriend(friendVo.getUserId(), userEntity.getId());
        if (bool) {
            throw new RunException(Constant.WARNING_CODE, "对方已是您的好友!");
        }
        // 添加好友
        LambdaQueryWrapper<FriendEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendEntity::getUserId, friendVo.getUserId())
                .eq(FriendEntity::getFriendId, userEntity.getId())
                .in(FriendEntity::getStatus, 0, 2);
        FriendEntity friend = this.getOne(wrapper);
        if (friend == null) {
            friend = new FriendEntity();
            friend.setUserId(friendVo.getUserId());
            friend.setFriendId(userEntity.getId());
            friend.setGroupId(friendVo.getGroupId());
            friend.setStatus(0);
            friend.setCreatedAt(new Date());
            this.save(friend);
        } else {
            friend.setGroupId(friendVo.getGroupId());
            friend.setStatus(0);
            friend.setCreatedAt(new Date());
            this.updateById(friend);
        }
    }

    @Override
    @Transactional
    public void accept(AcceptVo acceptVo) {
        FriendEntity friendEntity = this.getById(acceptVo.getId());
        friendEntity.setStatus(1);
        this.updateById(friendEntity);

        FriendEntity friend = new FriendEntity();
        friend.setUserId(friendEntity.getFriendId());
        friend.setFriendId(friendEntity.getUserId());
        friend.setStatus(1);
        friend.setGroupId(acceptVo.getGroupId());
        friend.setCreatedAt(new Date());
        this.save(friend);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setId(id);
        friendEntity.setStatus(status);
        this.updateById(friendEntity);
    }

    @Override
    public void move(AcceptVo acceptVo) {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setId(acceptVo.getId());
        friendEntity.setGroupId(acceptVo.getGroupId());
        this.updateById(friendEntity);
    }

    @Transactional
    @Override
    public void delete(Long userId, Long friendId) {
        LambdaQueryWrapper<FriendEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.eq(FriendEntity::getUserId, userId).eq(FriendEntity::getFriendId, friendId))
                .or(w -> w.eq(FriendEntity::getUserId, friendId).eq(FriendEntity::getFriendId, userId));
        this.remove(wrapper);
        // 删除聊天会话
        LambdaQueryWrapper<ConversationEntity> conversationWrapper = new LambdaQueryWrapper<>();
        conversationWrapper.and(w -> w.eq(ConversationEntity::getFrom, userId).eq(ConversationEntity::getTo, friendId))
                .or(w -> w.eq(ConversationEntity::getFrom, friendId).eq(ConversationEntity::getTo, userId));
        conversationService.remove(conversationWrapper);
    }

    @Override
    public List<ApplyDto> queryFriendApply(Long userId) {
        return baseMapper.queryFriendApply(userId);
    }

    @Override
    public Boolean validatedFriend(Long userId, Long friendId) {
        LambdaQueryWrapper<FriendEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.eq(FriendEntity::getUserId, userId).eq(FriendEntity::getFriendId, friendId))
                .or(w -> w.eq(FriendEntity::getUserId, friendId).eq(FriendEntity::getFriendId, userId));
        List<FriendEntity> list = this.list(wrapper);
        if (list.size() == 2) {
            if (list.get(0).getStatus() == 1 && list.get(1).getStatus() == 1) {
                return true;
            }
        }
        return false;
    }
}
