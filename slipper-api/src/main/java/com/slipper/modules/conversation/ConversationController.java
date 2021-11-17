package com.slipper.modules.conversation;

import com.slipper.common.utils.R;
import com.slipper.modules.AbstractController;
import com.slipper.service.conversation.service.ConversationService;
import com.slipper.service.friend.service.FriendService;
import com.slipper.service.friend.vo.AcceptVo;
import com.slipper.service.friend.vo.FriendVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 聊天会话
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@RestController
@RequestMapping("/im/conversation")
public class ConversationController extends AbstractController {
    @Resource
    private ConversationService conversationService;

    /**
     * 聊天会话列表
     *
     * @api {GET} /im/conversation/list list
     * @apiDescription 分组好友列表
     * @apiVersion 1.0.0
     * @apiGroup Conversation
     * @apiName list
     * @apiParamExample 请求参数示例
     * {}
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     *     data: [{
     *         	   id: '', // 会话ID
     *             type: '', // 类型：1-私聊 2-群聊
     *             unread_count: '', // 未读消息数量
     *             created_at: '', // 创建时间
     *             from: {
     *                 id: '', // 用户ID
     *                 username: '', // 用户名
     *                 nickname: '', // 昵称
     *                 avatar: '', // 头像
     *                 sex: '', // 性别 0-女 1-男 2-保密
     *                 mobile: '', // 手机号
     *             }
     *             to: {
     *                 id: '', // 用户ID
     *                 username: '', // 用户名
     *                 nickname: '', // 昵称
     *                 avatar: '', // 头像
     *                 sex: '', // 性别 0-女 1-男 2-保密
     *                 mobile: '', // 手机号
     *             }
     *             message: {
     *                 id: '', // 消息ID
     *                 content: '', // 文本内容
     *                 from: '', // 发送者ID
     *                 to: '', // 接收者ID
     *                 type_id: '', // 消息类型ID
     *                 type_name: '', // 消息类型名称
     *                 status: '', // 接收状态 0-未读 1-已读
     *                 created_at: '', // 发送时间
     *                 url: '', // 资源路径
     *             }
     *         }]
     *     }
     * }
     */
    @GetMapping("/list")
    public R list(){
        return R.success(conversationService.queryList(getUserId()));
    }

    /**
     * 添加聊天会话
     *
     * @api {GET} /im/conversation/create create
     * @apiDescription 添加聊天会话
     * @apiVersion 1.0.0
     * @apiGroup Conversation
     * @apiName create
     * @apiParamExample 请求参数示例
     * {
     *     id: '', // 用户ID
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     *     data: {
     *         	   id: '', // 会话ID
     *             type: '', // 类型：1-私聊 2-群聊
     *             unread_count: '', // 未读消息数量
     *             created_at: '', // 创建时间
     *             from: {
     *                 id: '', // 用户ID
     *                 username: '', // 用户名
     *                 nickname: '', // 昵称
     *                 avatar: '', // 头像
     *                 sex: '', // 性别 0-女 1-男 2-保密
     *                 mobile: '', // 手机号
     *             }
     *             to: {
     *                 id: '', // 用户ID
     *                 username: '', // 用户名
     *                 nickname: '', // 昵称
     *                 avatar: '', // 头像
     *                 sex: '', // 性别 0-女 1-男 2-保密
     *                 mobile: '', // 手机号
     *             }
     *             message: {
     *                 id: '', // 消息ID
     *                 content: '', // 文本内容
     *                 from: '', // 发送者ID
     *                 to: '', // 接收者ID
     *                 type_id: '', // 消息类型ID
     *                 type_name: '', // 消息类型名称
     *                 status: '', // 接收状态 0-未读 1-已读
     *                 created_at: '', // 发送时间
     *                 url: '', // 资源路径
     *             }
     *         }
     *     }
     * }
     */
    @PostMapping("/create")
    public R create(@RequestBody Long id) {
        return R.success(conversationService.create(getUserId(), id));
    }

    /**
     * 删除聊天会话
     *
     * @api {GET} /im/conversation/delete delete
     * @apiDescription 删除聊天会话
     * @apiVersion 1.0.0
     * @apiGroup Conversation
     * @apiName delete
     * @apiParamExample 请求参数示例
     * {
     *     id: '', // 聊天会话ID
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
        conversationService.removeById(id);
        return R.success();
    }
//
//    /**
//     * 接受好友请求
//     *
//     * @api {GET} /im/friend/accept accept
//     * @apiDescription 接受好友请求
//     * @apiVersion 1.0.0
//     * @apiGroup Friend
//     * @apiName accept
//     * @apiParamExample 请求参数示例
//     * {
//     *     id: '', // 申请ID
//     *     group_id, // 分组ID
//     * }
//     * @apiSuccessExample 响应结果示例
//     * {
//     *     code: 0,
//     *     status: 'success',
//     *     message: '成功!'
//     * }
//     */
//    @PostMapping("/accept")
//    public R accept(@RequestBody @Validated AcceptVo acceptVo) {
//        acceptVo.setUserId(getUserId());
//        friendService.accept(acceptVo);
//        return R.success();
//    }
//
//    /**
//     * 拒绝好友请求
//     *
//     * @api {GET} /im/friend/refuse refuse
//     * @apiDescription 拒绝好友请求
//     * @apiVersion 1.0.0
//     * @apiGroup Friend
//     * @apiName refuse
//     * @apiParamExample 请求参数示例
//     * {
//     *     id: '', // 申请ID
//     * }
//     * @apiSuccessExample 响应结果示例
//     * {
//     *     code: 0,
//     *     status: 'success',
//     *     message: '成功!'
//     * }
//     */
//    @PostMapping("/refuse")
//    public R refuse(@RequestBody Long id) {
//        friendService.updateStatus(id, 2);
//        return R.success();
//    }
//
//    /**
//     * 重新申请好友请求
//     *
//     * @api {GET} /im/friend/again again
//     * @apiDescription 重新申请好友请求
//     * @apiVersion 1.0.0
//     * @apiGroup Friend
//     * @apiName again
//     * @apiParamExample 请求参数示例
//     * {
//     *     id: '', // 申请ID
//     * }
//     * @apiSuccessExample 响应结果示例
//     * {
//     *     code: 0,
//     *     status: 'success',
//     *     message: '成功!'
//     * }
//     */
//    @PostMapping("/again")
//    public R again(@RequestBody Long id) {
//        friendService.updateStatus(id, 0);
//        return R.success();
//    }
//
//    /**
//     * 移动好友到分组
//     *
//     * @api {GET} /im/friend/move move
//     * @apiDescription 移动好友到分组
//     * @apiVersion 1.0.0
//     * @apiGroup Friend
//     * @apiName move
//     * @apiParamExample 请求参数示例
//     * {
//     *     id: '', // 申请ID
//     *     group_id, // 分组ID
//     * }
//     * @apiSuccessExample 响应结果示例
//     * {
//     *     code: 0,
//     *     status: 'success',
//     *     message: '成功!'
//     * }
//     */
//    @PostMapping("/move")
//    public R move(@RequestBody @Validated AcceptVo acceptVo) {
//        friendService.move(acceptVo);
//        return R.success();
//    }
//
//    /**
//     * 删除好友
//     *
//     * @api {GET} /im/friend/delete delete
//     * @apiDescription 删除好友
//     * @apiVersion 1.0.0
//     * @apiGroup Friend
//     * @apiName delete
//     * @apiParamExample 请求参数示例
//     * {
//     *     id: '', // 申请ID
//     * }
//     * @apiSuccessExample 响应结果示例
//     * {
//     *     code: 0,
//     *     status: 'success',
//     *     message: '成功!'
//     * }
//     */
//    @PostMapping("/delete")
//    public R delete(@RequestBody Long id) {
//        friendService.delete(getUserId(), id);
//        return R.success();
//    }
//
//    /**
//     * 获取好友申请列表
//     *
//     * @api {GET} /im/friend/apply/list list
//     * @apiDescription 分组好友列表
//     * @apiVersion 1.0.0
//     * @apiGroup Friend
//     * @apiName list
//     * @apiParamExample 请求参数示例
//     * {}
//     * @apiSuccessExample 响应结果示例
//     * {
//     *     code: 0,
//     *     status: 'success',
//     *     message: '成功!',
//     *     data: [{
//     *         	   id: '', // ID
//     *             remark: '', // 备注
//     *             group_id: '', // 分组ID
//     *             status: '', // 状态：0-请求添加好友 1-通过 2-拒绝
//     *             user_id: '', // 用户ID
//     *             username: '', // 用户账号
//     *             nickname: '', // 昵称
//     *             avatar: '', // 头像
//     *             sex: '', // 性别：0-女 1-男 2-保密
//     *         }]
//     *     }
//     * }
//     */
//    @GetMapping("/apply/list")
//    public R applyList(){
//        return R.success(friendService.queryFriendApply(getUserId()));
//    }
}
