package com.yuunik.vodservice.controller;

import com.yuunik.vodservice.service.VodService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "视频点播 api 接口服务")
@RestController
@RequestMapping("/vodservice/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("上传视频")
    @PostMapping("/upload")
    public R uploadVideo(@ApiParam(name = "videoFile", value = "视频文件", required = true) MultipartFile videoFile) {
        String videoId = vodService.uploadVideo(videoFile);
        return R.ok().data("videoId", videoId);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/deleteVodVideo/{videoId}")
    public R deleteVodVideo(@ApiParam(name = "videoId", value = "视频id", required = true) @PathVariable String videoId) {
        vodService.deleteVodVideo(videoId);
        return R.ok();
    }
}
