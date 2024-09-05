package com.yuunik.vodservice.controller;

import com.yuunik.vodservice.service.VodService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @ApiOperation("删除多个视频")
    @DeleteMapping("/batchDeleteVodVideo")
    public R batchDeleteVodVideo(@ApiParam(name = "videoIdList", value = "视频id列表", required = true) @RequestParam("videoList") List<String> videoIdList) {
        vodService.batchDeleteVodVideo(videoIdList);
        return R.ok();
    }

    @ApiOperation("根据视频id获取视频播放凭证")
    @GetMapping("/getVideoAuthByVideoSourceId/{videoSourceId}")
    public R getVideoAuthByVideoId(@ApiParam(name = "videoSourceId", value = "视频id", required = true) @PathVariable String videoSourceId) {
        try {
            String videoAuthCode = vodService.getVideoAuthCode(videoSourceId);
            return R.ok().data("videoAuth", videoAuthCode);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
