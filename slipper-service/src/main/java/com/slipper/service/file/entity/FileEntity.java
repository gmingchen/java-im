package com.slipper.service.file.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 原始名称
     */
    private String original;
    /**
     * 实际名称
     */
    private String actual;
    /**
     * 虚拟路径
     */
    private String url;
}
