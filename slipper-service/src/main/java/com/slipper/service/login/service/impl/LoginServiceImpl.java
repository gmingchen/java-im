package com.slipper.service.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.slipper.common.exception.RunException;
import com.slipper.common.utils.Constant;
import com.slipper.service.friendGroup.entity.FriendGroupEntity;
import com.slipper.service.friendGroup.service.FriendGroupService;
import com.slipper.service.login.service.LoginService;
import com.slipper.service.login.vo.LoginVo;
import com.slipper.service.token.entity.TokenEntity;
import com.slipper.service.token.service.TokenService;
import com.slipper.service.user.entity.UserEntity;
import com.slipper.service.user.service.UserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 登录
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;
    @Resource
    private FriendGroupService friendGroupService;

    @Override
    public TokenEntity login(LoginVo loginVo) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, loginVo.getUsername());
        UserEntity userEntity = userService.getOne(wrapper);

        if (userEntity == null) {
            throw new RunException(Constant.WARNING_CODE, "帐号不存在!");
        }

        if (!userEntity.getPassword().equals(new Sha256Hash(loginVo.getPassword(), userEntity.getSalt()).toHex())) {
            throw new RunException(Constant.WARNING_CODE, "密码不正确!");
        }

        return tokenService.createToken(userEntity.getId());
    }

    @Override
    @Transactional
    public void register(LoginVo loginVo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginVo.getUsername());
        userEntity.setPassword(loginVo.getPassword());
        userService.create(userEntity);
        // 创建默认好友分组
        FriendGroupEntity friendGroupEntity = new FriendGroupEntity();
        friendGroupEntity.setName("我的好友");
        friendGroupEntity.setUserId(userEntity.getId());
        friendGroupEntity.setCreatedAt(new Date());
        friendGroupService.create(friendGroupEntity);
    }

    @Override
    public void logout(Long adminId) {
        tokenService.destroyToken(adminId);
    }
}
