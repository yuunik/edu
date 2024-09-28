package com.yuunik.controller;

import com.yuunik.service.OssService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "阿里云oss上传接口")
@RestController
@RequestMapping("/ossservice/file")
public class OssController {
    @Autowired
    private OssService ossSerivce;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public R uploadFile(@ApiParam(name = "file", value = "文件", required = true) MultipartFile file) {
        try {
            String url = ossSerivce.uploadFile(file);
            return R.ok().data("url", url);
        } catch (Exception e) {
            // 输出异常
            e.printStackTrace();
            return R.error().message("上传失败");
        }
    }
}
