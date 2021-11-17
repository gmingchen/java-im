package com.slipper.modules.user;

import com.slipper.common.utils.R;
import com.slipper.modules.AbstractController;
import com.slipper.service.user.service.UserService;
import com.slipper.service.user.vo.UserBasicVo;
import com.slipper.service.user.vo.UserPasswordVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@RestController
@RequestMapping("/im/user")
public class UserController extends AbstractController {
    @Resource
    private UserService userService;

    /**
     * 当前登录用户信息
     *
     * @api {GET} /im/user/self/info selfInfo
     * @apiDescription 当前登录管理员信息
     * @apiVersion 1.0.0
     * @apiGroup User
     * @apiName selfInfo
     * @apiParamExample 请求参数示例
     * {}
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     *     data: {
     *         	   id: '', // ID
     *             username: '', // 帐号
     *             nickname: '', // 昵称
     *             avatar: '', // 头像
     *             sex: '', // 性别
     *             mobile: '', // 手机
     *             created_at: '', // 创建时间
     *         }
     *     }
     * }
     */
    @GetMapping("/self/info")
    public R selfInfo(){
        return R.success(userService.queryBasicById(getUserId()));
    }

    /**
     * 用户ID获取信息
     *
     * @api {GET} /im/user/info/{id} info
     * @apiDescription 用户ID获取信息
     * @apiVersion 1.0.0
     * @apiGroup User
     * @apiName info
     * @apiParamExample 请求参数示例
     * {}
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     *     data: {
     *         	   id: '', // ID
     *             username: '', // 帐号
     *             nickname: '', // 昵称
     *             avatar: '', // 头像
     *             mobile: '', // 手机
     *             sex: '', // 性别
     *             created_at: '', // 创建时间
     *         }
     *     }
     * }
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        return R.success(userService.queryBasicById(id));
    }

    /**
     * 编辑基础信息
     *
     * @api {POST} /im/user/update/basic updateBasic
     * @apiDescription 编辑基础信息
     * @apiVersion 1.0.0
     * @apiGroup User
     * @apiName updateBasic
     * @apiParamExample 请求参数示例
     * {
     *     nickname: '', // 昵称
     *     avatar: '', // 头像
     *     sex: '', // 性别：0-女 1-男 2-保密
     *     mobile: '' // 手机号
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/update/basic")
    public R updateBasic(@RequestBody @Validated UserBasicVo userBasicVo) {
        userBasicVo.setId(getUserId());
        userService.updateBasic(userBasicVo);
        return R.success();
    }

    /**
     * 编辑密码
     *
     * @api {POST} /im/user/update/password updatePassword
     * @apiDescription 编辑密码
     * @apiVersion 1.0.0
     * @apiGroup User
     * @apiName updatePassword
     * @apiParamExample 请求参数示例
     * {
     *     old_password: '', // 原密码
     *     new_password: '', // 新密码
     *     confirm_password: '', // 确认密码
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/update/password")
    public R updatePassword(@RequestBody @Validated UserPasswordVo userPasswordVo) {
        userPasswordVo.setId(getUserId());
        userService.updatePassword(userPasswordVo);
        return R.success();
    }

}
