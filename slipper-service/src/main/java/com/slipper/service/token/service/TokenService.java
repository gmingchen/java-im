package com.slipper.service.token.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.slipper.service.token.entity.TokenEntity;

/**
 * Token
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public interface TokenService extends IService<TokenEntity> {

    TokenEntity createToken (Long userId);

    void destroyToken (Long userId);

    TokenEntity queryByToken (String token);

    TokenEntity queryByUserId (Long userId);

}

