package com.slipper.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slipper.common.exception.RunException;
import com.slipper.common.utils.Constant;
import com.slipper.service.user.dao.UserDao;
import com.slipper.service.user.entity.UserEntity;
import com.slipper.service.user.service.UserService;
import com.slipper.service.user.vo.UserBasicVo;
import com.slipper.service.user.vo.UserPasswordVo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Override
    public UserEntity queryBasicById(Long id) {
        UserEntity userEntity = this.getById(id);
        userEntity.setPassword(null);
        userEntity.setSalt(null);
        return userEntity;
    }

    @Override
    public UserEntity queryBasicByUsername(String Username) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, Username);
        UserEntity userEntity = this.getOne(wrapper);
        if (userEntity == null) {
            throw new RunException(Constant.WARNING_CODE, "该用户不存在!");
        }
        userEntity.setPassword(null);
        userEntity.setSalt(null);
        return userEntity;
    }

    @Override
    public void create(UserEntity userEntity) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, userEntity.getUsername());
        int count = this.count(wrapper);
        if (count != 0) {
            throw new RunException(Constant.WARNING_CODE,"该用户已注册!");
        }

        // 加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String password = new Sha256Hash(userEntity.getPassword(), salt).toHex();
        userEntity.setSalt(salt);
        userEntity.setPassword(password);
        userEntity.setCreatedAt(new Date());

        this.save(userEntity);
    }

    @Override
    public void updateBasic(UserBasicVo userBasicVo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userBasicVo.getId());
        userEntity.setNickname(userBasicVo.getNickname());
        userEntity.setSex(userBasicVo.getSex());
        userEntity.setMobile(userBasicVo.getMobile());
        userEntity.setAvatar(userBasicVo.getAvatar());

        this.updateById(userEntity);
    }

    @Override
    public void updatePassword(UserPasswordVo userPasswordVo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userPasswordVo.getId());

        if (!userPasswordVo.getNewPassword().equals(userPasswordVo.getConfirmPassword())) {
            throw new RunException(Constant.WARNING_CODE, "新密码与确认密码不一致!");
        }

        UserEntity user = this.getById(userPasswordVo.getId());
        String oldPassword = new Sha256Hash(userPasswordVo.getOldPassword(), user.getSalt()).toHex();
        if (!user.getPassword().equals(oldPassword)) {
            throw new RunException(Constant.WARNING_CODE, "原密码不正确!");
        }

        String newPassword = new Sha256Hash(userPasswordVo.getNewPassword(), user.getSalt()).toHex();
        userEntity.setPassword(newPassword);

        this.updateById(userEntity);
    }
}