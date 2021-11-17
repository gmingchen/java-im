package com.slipper.modules.friend;

import com.slipper.common.utils.R;
import com.slipper.modules.AbstractController;
import com.slipper.netty.service.NettyService;
import com.slipper.service.friend.service.FriendService;
import com.slipper.service.friend.vo.AcceptVo;
import com.slipper.service.friend.vo.FriendVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 好友
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@RestController
@RequestMapping("/im/friend")
public class FriendController extends AbstractController {
    @Resource
    private FriendService friendService;
    @Resource
    private NettyService nettyService;

    /**
     * 分组好友列表
     *
     * @api {GET} /im/friend/list list
     * @apiDescription 分组好友列表
     * @apiVersion 1.0.0
     * @apiGroup Friend
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
     *             remark: '', // 备注
     *             group_id: '', // 分组ID
     *             status: '', // 状态：0-请求添加好友 1-通过 2-拒绝
     *             user_id: '', // 用户ID
     *             username: '', // 用户账号
     *             nickname: '', // 昵称
     *             avatar: '', // 头像
     *             sex: '', // 性别：0-女 1-男 2-保密
     *         }]
     *     }
     * }
     */
    @GetMapping("/list")
    public R list(@RequestParam("group_id") Long groupId){
        return R.success(friendService.queryFriendByGroupId(groupId));
    }

    /**
     * 添加好友到分组列表
     *
     * @api {GET} /im/friend/create create
     * @apiDescription 添加好友到分组列表
     * @apiVersion 1.0.0
     * @apiGroup Friend
     * @apiName create
     * @apiParamExample 请求参数示例
     * {}
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     *     data: [{
     *             username: '', // 用户帐号
     *             group_id: '', // 分组ID
     *         }]
     *     }
     * }
     */
    @PostMapping("/create")
    public R create(@RequestBody @Validated FriendVo friendVo) {
        friendVo.setUserId(getUserId());
//        friendService.create(friendVo);
        nettyService.createFriend(friendVo);
        return R.success();
    }

    /**
     * 接受好友请求
     *
     * @api {GET} /im/friend/accept accept
     * @apiDescription 接受好友请求
     * @apiVersion 1.0.0
     * @apiGroup Friend
     * @apiName accept
     * @apiParamExample 请求参数示例
     * {
     *     id: '', // 申请ID
     *     group_id, // 分组ID
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/accept")
    public R accept(@RequestBody @Validated AcceptVo acceptVo) {
        acceptVo.setUserId(getUserId());
//        friendService.accept(acceptVo);
        nettyService.acceptFriend(acceptVo);
        return R.success();
    }

    /**
     * 拒绝好友请求
     *
     * @api {GET} /im/friend/refuse refuse
     * @apiDescription 拒绝好友请求
     * @apiVersion 1.0.0
     * @apiGroup Friend
     * @apiName refuse
     * @apiParamExample 请求参数示例
     * {
     *     id: '', // 申请ID
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/refuse")
    public R refuse(@RequestBody Long id) {
//        friendService.updateStatus(id, 2);
        nettyService.updateFriendStatus(id, 2);
        return R.success();
    }

    /**
     * 重新申请好友请求
     *
     * @api {GET} /im/friend/again again
     * @apiDescription 重新申请好友请求
     * @apiVersion 1.0.0
     * @apiGroup Friend
     * @apiName again
     * @apiParamExample 请求参数示例
     * {
     *     id: '', // 申请ID
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/again")
    public R again(@RequestBody Long id) {
//        friendService.updateStatus(id, 0);
        nettyService.updateFriendStatus(id, 0);
        return R.success();
    }

    /**
     * 移动好友到分组
     *
     * @api {GET} /im/friend/move move
     * @apiDescription 移动好友到分组
     * @apiVersion 1.0.0
     * @apiGroup Friend
     * @apiName move
     * @apiParamExample 请求参数示例
     * {
     *     id: '', // 申请ID
     *     group_id, // 分组ID
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     * }
     */
    @PostMapping("/move")
    public R move(@RequestBody @Validated AcceptVo acceptVo) {
        friendService.move(acceptVo);
        return R.success();
    }

    /**
     * 删除好友
     *
     * @api {GET} /im/friend/delete delete
     * @apiDescription 删除好友
     * @apiVersion 1.0.0
     * @apiGroup Friend
     * @apiName delete
     * @apiParamExample 请求参数示例
     * {
     *     id: '', // 申请ID
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
//        friendService.delete(getUserId(), id);
        nettyService.deleteFriend(getUserId(), id);
        return R.success();
    }

    /**
     * 获取好友申请列表
     *
     * @api {GET} /im/friend/apply/list list
     * @apiDescription 分组好友列表
     * @apiVersion 1.0.0
     * @apiGroup Friend
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
     *             remark: '', // 备注
     *             group_id: '', // 分组ID
     *             status: '', // 状态：0-请求添加好友 1-通过 2-拒绝
     *             user_id: '', // 用户ID
     *             username: '', // 用户账号
     *             nickname: '', // 昵称
     *             avatar: '', // 头像
     *             sex: '', // 性别：0-女 1-男 2-保密
     *         }]
     *     }
     * }
     */
    @GetMapping("/apply/list")
    public R applyList(){
        return R.success(friendService.queryFriendApply(getUserId()));
    }
}
