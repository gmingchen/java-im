package com.slipper.service.friend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slipper.service.friend.dto.ApplyDto;
import com.slipper.service.friend.dto.FriendDto;
import com.slipper.service.friend.entity.FriendEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Mapper
public interface FriendDao extends BaseMapper<FriendEntity> {

    /**
     * 分组id查询好友列表
     * @param groupId
     * @return
     */
    List<FriendDto> queryFriendByGroupId(@Param("groupId") Long groupId);

    /**
     * 查询好友申请列表
     * @param userId
     * @return
     */
    List<ApplyDto> queryFriendApply(Long userId);
	
}
