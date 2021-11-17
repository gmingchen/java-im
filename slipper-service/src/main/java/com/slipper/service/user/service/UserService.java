package com.slipper.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.slipper.service.user.entity.UserEntity;
import com.slipper.service.user.vo.UserBasicVo;
import com.slipper.service.user.vo.UserPasswordVo;

/**
 * 用户
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public interface UserService extends IService<UserEntity> {

    /**
     * ID查询用户基础信息
     * @param id 用户ID
     */
    UserEntity queryBasicById(Long id);

    /**
     * 帐号查询用户基础信息
     * @param Username 帐号
     */
    UserEntity queryBasicByUsername(String Username);

    /**
     * 新增
     *
     * @param userEntity 用户
     */
    void create(UserEntity userEntity);

    /**
     * 编辑基础信息
     * @param userBasicVo 用户基础信息VO
     */
    void updateBasic(UserBasicVo userBasicVo);

    /**
     * 编辑密码
     * @param userPasswordVo 用户密码VO
     */
    void updatePassword(UserPasswordVo userPasswordVo);

}

