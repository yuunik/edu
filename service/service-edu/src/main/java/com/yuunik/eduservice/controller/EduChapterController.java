package com.yuunik.eduservice.controller;


import com.yuunik.eduservice.entity.EduChapter;
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
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation("获取课程章节列表")
    @GetMapping("/getChapterList/{courseId}")
    public R getChapterList(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable String courseId) {
        List<ChapterVo> chpaterVoList = eduChapterService.getChapterList(courseId);
        return R.ok().data("chapterList", chpaterVoList);
    }

    @ApiOperation("新增课程章节")
    @PostMapping("/addChapter")
    public R addChapter(@ApiParam(name = "eduChapter", value = "新增的课程章节", required = true) @RequestBody EduChapter eduChapter) {
        boolean result = eduChapterService.save(eduChapter);
        return result ? R.ok() : R.error();
    }

    @ApiOperation("删除课程章节")
    @DeleteMapping("/removeChapter/{id}")
    public R removeChapter(@ApiParam(name = "id", value = "课程章节 id", required = true) @PathVariable String id) {
        boolean result = eduChapterService.removeChapter(id);
        return result ? R.ok() : R.error();
    }

    @ApiOperation("修改课程章节")
    @PostMapping("/editChapter")
    public R editChapter(@ApiParam(name = "eduChapter", value = "修改的课程章节信息", required = true) @RequestBody EduChapter eduChapter) {
        boolean result = eduChapterService.updateById(eduChapter);
        return result ? R.ok() : R.error();
    }

    @ApiOperation("查询课程章节")
    @GetMapping("/getChapterInfo/{id}")
    public R getChapterInfo(@ApiParam(name = "id", value = "课程章节 id", required = true) @PathVariable String id) {
        EduChapter eduChapter = eduChapterService.getById(id);
        if (eduChapter == null) {
            return R.error();
        }
        return R.ok().data("eduChapter", eduChapter);
    }
}

