package com.yuunik.eduservice.controller.front;

import com.yuunik.eduservice.entity.EduCourse;
import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.service.EduCourseService;
import com.yuunik.eduservice.service.EduTeacherService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "前台首页数据接口")
@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("获取首页的课程及讲师信息")
    @GetMapping("/getIndexInfoList")
    public R getIndexInfoList() {
        // 获取热门课程信息列表
        List<EduCourse> courseList = eduCourseService.getPopularCourseList();
        // 获取名师大咖列表
        List<EduTeacher> teacherList = eduTeacherService.getFamousTeacherList();
        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

}
