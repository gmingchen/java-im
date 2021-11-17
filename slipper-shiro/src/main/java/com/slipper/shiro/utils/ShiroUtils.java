package com.slipper.shiro.utils;

import com.slipper.service.user.entity.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Shiro 工具类
 *
 * @author Loafer
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static UserEntity getUser() {
        return (UserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getUserId() {
        return getUser().getId();
    }

}
