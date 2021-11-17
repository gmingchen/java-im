package com.slipper.service.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.slipper.common.utils.RPage;
import com.slipper.service.conversation.dto.ConversationDto;
import com.slipper.service.conversation.entity.ConversationEntity;
import com.slipper.service.message.entity.PrivateMessageEntity;

import java.util.List;
import java.util.Map;

/**
 * 聊天会话
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public interface PrivateMessageService extends IService<PrivateMessageEntity> {

    /**
     * 分页列表
     * @param params
     * @return
     */
    RPage<PrivateMessageEntity> queryPage(Map<String, Object> params, Long userId);

    /**
     * 设置消息已读未读
     * @param status 0-未读 1-已读
     * @param ids
     * @param userId
     * @param friendId
     */
    void updateStatus(Integer status, List<Long> ids, Long userId, Long friendId);
}

