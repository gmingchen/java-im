package com.slipper.service.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.slipper.common.validator.group.Create;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 私聊消息
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "message")
public class PrivateMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空", groups = {Create.class})
    private String content;
    /**
     * 发送者ID
     */
    @TableField("`from`")
    @NotNull(message = "发送者ID不能为空", groups = {Create.class})
    private Long from;
    /**
     * 接收者ID
     */
    @TableField("`to`")
    @NotNull(message = "接收者ID不能为空", groups = {Create.class})
    private Long to;
    /**
     * 消息类型
     */
    @NotNull(message = "消息类型不能为空", groups = {Create.class})
    private Integer type;
    /**
     * 接收状态 0-未读 1-已读
     */
    private Integer status;
    /**
     * 创建时间 毫秒
     */
    @JsonProperty("created_at")
    private Long createdAt;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 消息确认字段
     */
    @TableField(exist = false)
    private String ack;
}
