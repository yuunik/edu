package com.yuunik.eduservice.controller;


import com.yuunik.eduservice.entity.chapter.ChapterVo;
import com.yuunik.eduservice.service.EduChapterService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
@Api(description = "课程章节模块接口")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation("获取课程章节列表")
    @GetMapping("/getChapterList/{courseId}")
    public R getChapterList(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable String courseId) {
        List<ChapterVo> chpaterVoList = eduChapterService.getChapterList(courseId);
        return R.ok().data("chapterList", chpaterVoList);
    }
}

