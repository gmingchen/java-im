package com.slipper.shiro.service;


import com.slipper.service.token.entity.TokenEntity;
import com.slipper.service.user.entity.UserEntity;

import java.util.Set;

/**
 * 菜单
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public interface ShiroService {

    /**
     * 获取token
     * @param token
     * @return
     */
    TokenEntity queryTokenByToken(String token);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserEntity queryUserByUserId(Long userId);

}

