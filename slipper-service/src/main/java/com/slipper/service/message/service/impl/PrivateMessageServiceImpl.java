package com.slipper.service.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slipper.common.utils.Query;
import com.slipper.common.utils.RPage;
import com.slipper.service.message.dao.PrivateMessageDao;
import com.slipper.service.message.entity.PrivateMessageEntity;
import com.slipper.service.message.service.PrivateMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("privateMessageService")
public class PrivateMessageServiceImpl extends ServiceImpl<PrivateMessageDao, PrivateMessageEntity> implements PrivateMessageService {

    @Override
    public RPage<PrivateMessageEntity> queryPage(Map<String, Object> params, Long userId) {
        Long friendId = Long.valueOf(params.get("friend_id").toString());
        Long times = null;
        if (StringUtils.isNotBlank((String)params.get("times"))) {
            times = Long.valueOf(params.get("times").toString());
        }
        Page<PrivateMessageEntity> page = new Query<PrivateMessageEntity>().getPage(params);
        LambdaQueryWrapper<PrivateMessageEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.lt(times != null, PrivateMessageEntity::getCreatedAt, times)
                .and(w -> w.eq(PrivateMessageEntity::getFrom, userId).eq(PrivateMessageEntity::getTo, friendId))
                .or(w -> w.eq(PrivateMessageEntity::getFrom, friendId).eq(PrivateMessageEntity::getTo, userId))
                .orderByDesc(PrivateMessageEntity::getCreatedAt);

        // 设置消息已读
        this.updateStatus(1, null, userId, friendId);

        return new RPage<>(this.page(page, wrapper));
    }

    @Override
    public void updateStatus(Integer status, List<Long> ids, Long userId, Long friendId) {
        LambdaUpdateWrapper<PrivateMessageEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(PrivateMessageEntity::getStatus, status == 0 ? 1 : 0);
        if (ids != null) {
            wrapper.in(PrivateMessageEntity::getId, ids);
        }
        if (userId != null && friendId != null) {
            wrapper.eq(PrivateMessageEntity::getFrom,friendId)
                    .eq(PrivateMessageEntity::getTo, userId);
        }
        wrapper.set(PrivateMessageEntity::getStatus, status);
        this.update(wrapper);
    }
}