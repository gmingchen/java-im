package com.slipper.modules.login;

import com.slipper.common.utils.R;
import com.slipper.modules.AbstractController;
import com.slipper.service.login.service.LoginService;
import com.slipper.service.login.vo.LoginVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录 / 注册 / 登出
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@RestController
@RequestMapping("/im")
public class LoginController extends AbstractController {

    @Resource
    private LoginService loginService;

    /**
     * 登录
     *
     * @api {POST} /im/login
     * @apiDescription 登录
     * @apiVersion 1.0.0
     * @apiGroup Login
     * @apiName login
     * @apiParamExample 请求参数示例
     * {
     *     username: 1, // 帐号
     *     password: 1, // 密码
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     *     data: {
     *         user_id: 1, // 用户ID
     *         token: 1, // token
     *         expired_at: 1, // 过期时间
     *         updated_at: 1 // 更新时间
     *     }
     * }
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated LoginVo loginVo) {
        return R.success(loginService.login(loginVo));
    }

    /**
     * 注册
     *
     * @api {POST} /im/register
     * @apiDescription 注册
     * @apiVersion 1.0.0
     * @apiGroup Login
     * @apiName register
     * @apiParamExample 请求参数示例
     * {
     *     username: 1, // 帐号
     *     password: 1, // 密码
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/register")
    public R register(@RequestBody @Validated LoginVo loginVo) {
        loginService.register(loginVo);
        return R.success();
    }

    /**
     * 登出
     *
     * @api {POST} /im/logout
     * @apiDescription 登录
     * @apiVersion 1.0.0
     * @apiGroup Login
     * @apiName logout
     * @apiParamExample 请求参数示例
     * {}
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/logout")
    public R logout() {
        loginService.logout(getUserId());
        return R.success();
    }

}
