package com.slipper.service.conversation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slipper.service.conversation.dao.ConversationDao;
import com.slipper.service.conversation.dto.ConversationDto;
import com.slipper.service.conversation.entity.ConversationEntity;
import com.slipper.service.conversation.service.ConversationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("conversationService")
public class ConversationServiceImpl extends ServiceImpl<ConversationDao, ConversationEntity> implements ConversationService {

    @Override
    public List<ConversationDto> queryList(Long userId) {
        return baseMapper.queryList(userId);
    }

    @Transactional
    @Override
    public ConversationDto create(Long userId, Long toUserId) {
        Date now = new Date();
        ConversationEntity conversation = new ConversationEntity();
        conversation.setFrom(userId);
        conversation.setTo(toUserId);
        conversation.setType(1);
        conversation.setCreatedAt(now);
        this.save(conversation);

        return baseMapper.queryById(conversation.getId());
    }
}