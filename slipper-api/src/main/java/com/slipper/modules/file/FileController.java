package com.slipper.modules.file;

import com.slipper.common.utils.Constant;
import com.slipper.common.utils.R;
import com.slipper.modules.AbstractController;
import com.slipper.service.file.entity.FileEntity;
import com.slipper.service.file.service.FileService;
import com.slipper.service.message.service.PrivateMessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * 文件
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
@RestController
@RequestMapping("/im/file")
public class FileController extends AbstractController {
    @Resource
    private FileService fileService;

    /**
     * 文件上传
     *
     * @api {POST} /im/file/upload upload
     * @apiDescription 上传图片
     * @apiVersion 1.0.0
     * @apiGroup File
     * @apiName upload
     * @apiParamExample 请求参数示例
     * {
     *     file: (binary), // 文件流
     * }
     * @apiSuccessExample 响应结果示例
     * {
     *     code: 0,
     *     status: 'success',
     *     message: '成功!'
     *     data: {
     *         original: '', // 原始名称
     *         actual: '', // 实际名称
     *         url: '', // 虚拟路径
     *     }
     * }
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file){
        FileEntity fileEntity = fileService.create(file);
        return R.success(fileEntity);
    }
}
