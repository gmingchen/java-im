package com.slipper.service.friendGroup.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slipper.service.friendGroup.dto.FriendGroupDto;
import com.slipper.service.friendGroup.entity.FriendGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友分组
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Mapper
public interface FriendGroupDao extends BaseMapper<FriendGroupEntity> {
    /**
     * 查询分组 及好友
     * @return
     */
    List<FriendGroupDto> queryGroupFriend(@Param("userId") Long userId);
}
