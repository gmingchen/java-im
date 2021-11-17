package com.slipper.shiro.service.impl;

import com.slipper.service.token.dao.TokenDao;
import com.slipper.service.token.entity.TokenEntity;
import com.slipper.service.user.dao.UserDao;
import com.slipper.service.user.entity.UserEntity;
import com.slipper.shiro.service.ShiroService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * shiro
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {
    @Resource
    private TokenDao tokenDao;
    @Resource
    private UserDao userDao;

    @Override
    public TokenEntity queryTokenByToken(String token) {
        return tokenDao.queryByToken(token);
    }

    @Override
    public UserEntity queryUserByUserId(Long userId) {
        return userDao.selectById(userId);
    }

}