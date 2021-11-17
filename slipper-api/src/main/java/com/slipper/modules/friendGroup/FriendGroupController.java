package com.slipper.modules.friendGroup;

import com.slipper.common.utils.R;
import com.slipper.modules.AbstractController;
import com.slipper.service.friendGroup.entity.FriendGroupEntity;
import com.slipper.service.friendGroup.service.FriendGroupService;
import com.slipper.service.user.service.UserService;
import com.slipper.service.user.vo.UserBasicVo;
import com.slipper.service.user.vo.UserPasswordVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 好友分组
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@RestController
@RequestMapping("/im/friend/group")
public class FriendGroupController extends AbstractController {
    @Resource
    private FriendGroupService friendGroupService;

    /**
     * 分组 及好友列表
     *
     * @api {GET} /im/friend/group/list list
     * @apiDescription 分组列表
     * @apiVersion 1.0.0
     * @apiGroup FriendGroup
     * @apiName list
     * @apiParamExample 请求参数示例
     * {}
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     *     data: [{
     *         	   id: '', // ID
     *             name: '', // 帐号
     *             user_id: '', // 用户ID
     *             can_deleted: '', // 是否可删除：0-否 1-是
     *             created_at: '', // 创建时间
     *             friends: [{
     *                 id: '', // ID
     *                 remark: '', // 备注
     *                 group_id: '', // 分组ID
     *                 status: '', // 状态：0-请求添加好友 1-通过 2-拒绝
     *                 user_id: '', // 用户ID
     *                 username: '', // 用户账号
     *                 nickname: '', // 昵称
     *                 avatar: '', // 头像
     *                 sex: '', // 性别：0-女 1-男 2-保密
     *             }]
     *         }]
     *     }
     * }
     */
    @GetMapping("/list")
    public R list(){
        return R.success(friendGroupService.queryGroupFriend(getUserId()));
    }

    /**
     * 新增分组
     *
     * @api {GET} /im/friend/group/create create
     * @apiDescription 新增分组
     * @apiVersion 1.0.0
     * @apiGroup FriendGroup
     * @apiName create
     * @apiParamExample 请求参数示例
     * {
     *     name: '', // 分组名称
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     *     data: [{
     *         	   id: '', // ID
     *             name: '', // 帐号
     *             user_id: '', // 用户ID
     *             can_deleted: '', // 是否可删除：0-否 1-是
     *             created_at: '', // 创建时间
     *         }]
     *     }
     * }
     */
    @PostMapping("/create")
    public R create(@RequestBody @Validated FriendGroupEntity friendGroupEntity) {
        friendGroupEntity.setUserId(getUserId());
        return R.success(friendGroupService.create(friendGroupEntity));
    }

    /**
     * 分组删除
     *
     * @api {GET} /im/friend/group/delete delete
     * @apiDescription 分组删除
     * @apiVersion 1.0.0
     * @apiGroup FriendGroup
     * @apiName delete
     * @apiParamExample 请求参数示例
     * {
     *     id: '', // 分组ID
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long id) {
        friendGroupService.delete(id);
        return R.success();
    }
}
