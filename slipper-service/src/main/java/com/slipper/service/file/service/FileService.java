package com.slipper.service.file.service;

import com.slipper.service.file.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public interface FileService {

    /**
     * 文件创建存储
     * @param file
     * @return
     */
    FileEntity create(MultipartFile file);

}

