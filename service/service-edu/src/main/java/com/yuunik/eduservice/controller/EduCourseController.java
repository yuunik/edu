package com.yuunik.eduservice.controller;


import com.yuunik.eduservice.entity.vo.CourseInfoVO;
import com.yuunik.eduservice.entity.vo.CoursePublishVo;
import com.yuunik.eduservice.service.EduCourseService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程管理模块 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
@Api(description = "课程管理模块接口")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("新增课程基本信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@ApiParam(name = "courseInfoVO", value = "课程基本信息", required = true) @RequestBody CourseInfoVO courseInfoVO) {
        // 调用接口, 新增课程基本信息
        String id = eduCourseService.saveCourseInfo(courseInfoVO);
        return R.ok().data("courseId", id);
    }

    @ApiOperation("获取课程详情")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVO courseInfoVO = eduCourseService.selectCourseInfo(courseId);
        return R.ok().data("courseInfo", courseInfoVO);
    }

    @ApiOperation("修改课程信息")
    @PostMapping("/editCourseInfo")
    public R editCourseInfo(@ApiParam(name = "courseInfo", value = "课程信息", required = true)@RequestBody CourseInfoVO courseInfoVO) {
        eduCourseService.updateCourseInfo(courseInfoVO);
        return R.ok();
    }

    @ApiOperation("获取发布课程的详情")
    @GetMapping("/getCoursePublishInfo/{id}")
    public R getCoursePublishInfo(@ApiParam(name = "id", value = "课程 id", required = true) @PathVariable String id) {
        CoursePublishVo coursePublishInfo = eduCourseService.getCoursePublishInfo(id);
        return R.ok().data("coursePublishInfo", coursePublishInfo);
    }
}

