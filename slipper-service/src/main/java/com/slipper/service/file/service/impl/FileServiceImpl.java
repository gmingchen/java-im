package com.slipper.service.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slipper.common.exception.RunException;
import com.slipper.common.utils.FileUtils;
import com.slipper.common.utils.Query;
import com.slipper.common.utils.RPage;
import com.slipper.service.file.entity.FileEntity;
import com.slipper.service.message.dao.PrivateMessageDao;
import com.slipper.service.message.entity.PrivateMessageEntity;
import com.slipper.service.file.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Override
    public FileEntity create(MultipartFile file) {
        FileEntity fileEntity = setFileEntity(file, "http://resource.gumingchen.icu", "images");
        Boolean bool = FileUtils.save(file, "/home/static/images/" + fileEntity.getActual());
        if (!bool) {
            throw new RunException("文件上传失败!");
        }

        return fileEntity;
    }


    /**
     * 获取文件对象
     * @param file
     * @return
     */
    private FileEntity setFileEntity(MultipartFile file, String url, String prefix) {
        FileEntity fileEntity = new FileEntity();
        // 设置文件名
        fileEntity.setOriginal(file.getOriginalFilename());
        // 获取扩展名
        String extension = fileEntity.getOriginal().substring(fileEntity
                .getOriginal()
                .lastIndexOf("."));
        // 设置文件实际名称
        fileEntity.setActual(UUID.randomUUID().toString()
                + extension);
        // 设置文件虚拟路径
        fileEntity.setUrl(url + (StringUtils.isNotBlank(prefix) ? ("/" + prefix) : "") + "/" + fileEntity.getActual());

        return fileEntity;
    }
}