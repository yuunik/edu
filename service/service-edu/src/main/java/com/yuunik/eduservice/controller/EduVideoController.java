package com.yuunik.eduservice.controller;


import com.yuunik.eduservice.entity.EduVideo;
import com.yuunik.eduservice.service.EduVideoService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
@Api(description = "课程小节模块接口")
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @ApiOperation("新增课程小节")
    @PostMapping("/addVideo")
    public R addVideo(@ApiParam(name = "eduVideo", value = "新增的课程小节", required = true) @RequestBody EduVideo eduVideo) {
        boolean result = eduVideoService.save(eduVideo);
        return result ? R.ok() : R.error();
    }

    @ApiOperation("删除课程小节")
    @DeleteMapping("/removeVideo/{id}")
    public R removeVideo(@ApiParam(name = "id", value = "课程小节 id", required = true) @PathVariable String id) {
        eduVideoService.removeVideoById(id);
        return R.ok();
    }

    @ApiOperation("修改课程小节")
    @PostMapping("/editVideoInfo")
    public R editVideoInfo(@ApiParam(name = "eduVideo", value = "修改的课程小节信息", required = true) @RequestBody EduVideo eduVideo) {
        boolean result = eduVideoService.updateById(eduVideo);
        return result ? R.ok() : R.error();
    }

    @ApiOperation("获取课程小节详情")
    @GetMapping("/getVideoInfo/{id}")
    public R getVideoInfo(@ApiParam(name = "id", value = "课程小节 id", required = true) @PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        if (eduVideo == null) {
            return R.error().message("获取课程小节详情失败");
        }
        return R.ok().data("eduVideo", eduVideo);
    }

}

