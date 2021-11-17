package com.slipper.service.conversation.entity;

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
 * 聊天会话
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "conversation")
public class ConversationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 发送者ID
     */
    @NotNull(message = "发送者ID不能为空", groups = {Create.class})
    @TableField("`from`")
    private Long from;
    /**
     * 接收者ID
     */
    @NotNull(message = "接收者ID不能为空", groups = {Create.class})
    @TableField("`to`")
    private Long to;
    /**
     * 类型：1-私聊 2-群聊
     */
    @NotNull(message = "密码不能为空", groups = {Create.class})
    private Integer type;
    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Date createdAt;
}
