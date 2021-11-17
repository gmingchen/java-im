package com.slipper.modules.message;

import com.slipper.common.utils.R;
import com.slipper.modules.AbstractController;
import com.slipper.service.conversation.service.ConversationService;
import com.slipper.service.message.service.PrivateMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * 私聊信息
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@RestController
@RequestMapping("/im/private/message")
public class PrivateMessageController extends AbstractController {
    @Resource
    private PrivateMessageService privateMessageService;

    /**
     * 私聊信息分页
     *
     * @api {GET} /im/private/message/page page
     * @apiDescription 私聊信息分页
     * @apiVersion 1.0.0
     * @apiGroup PrivateMessage
     * @apiName page
     * @apiParamExample 请求参数示例
     * {
     *     current: 1, // 当前页
     *     size: 10, // 页面大小
     *     friend_id: '' // 好友ID
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     *     data: {
     *         current: 1, // 当前页
     *         size: 1, // 页面大小
     *         total: 1, // 总条数
     *         pages: 1, // 总页数
     *         list: [{
     *              id: '', // 消息ID
     *              content: '', // 文本内容
     *              from: '', // 发送者ID
     *              to: '', // 接收者ID
     *              type_id: '', // 消息类型ID
     *              type_name: '', // 消息类型名称
     *              status: '', // 接收状态 0-未读 1-已读
     *              created_at: '', // 发送时间
     *              url: '', // 资源路径
     *         }]
     *     }
     * }
     */
    @GetMapping("/page")
    public R list(@RequestParam Map<String, Object> params){
        return R.success(privateMessageService.queryPage(params, getUserId()));
    }

    /**
     * 设置私聊信息已读 未读
     *
     * @api {GET} /im/private/message/status status
     * @apiDescription 设置私聊信息已读 未读
     * @apiVersion 1.0.0
     * @apiGroup PrivateMessage
     * @apiName status
     * @apiParamExample 请求参数示例
     * {
     *     ids: [], // ID
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!',
     * }
     */
    @PostMapping("/status")
    public R status(@RequestBody Long[] ids){
        privateMessageService.updateStatus(1, Arrays.asList(ids), null, null);
        return R.success();
    }

}
