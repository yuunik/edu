package com.yuunik.eduservice.controller.front;

import com.yuunik.eduservice.entity.front.CourseQueryVo;
import com.yuunik.eduservice.service.EduCourseService;
import com.yuunik.utilscommon.R;
import com.yuunik.utilscommon.orderVo.CourseWebVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(description = "前台用户课程接口")
@RestController
@RequestMapping("/eduservice/front-end/course")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("条件分页查询课程列表信息")
    @PostMapping("/pageCourseListByCondition/{current}/{pageSize}")
    public R pageCourseListByCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                                       @ApiParam(name = "pageSize", value = "每页记录条数", required = true) @PathVariable long pageSize,
                                       @ApiParam(name = "courseVo", value = "条件查询对象", required = false) @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Map<String, Object> result = eduCourseService.pageCourseListByCondition(current, pageSize, courseQueryVo);
        return R.ok().data(result);
    }

    @ApiOperation("获取课程详情")
    @GetMapping("/getCourseInfo/{id}")
    public R getCourseInfo(@ApiParam(name = "id", value = "课程id", required = true) @PathVariable String id, HttpServletRequest request) {
        Map<String, Object> result = eduCourseService.getCourseFrontInfo(id, request);
        return R.ok().data(result);
    }

    @ApiOperation("获取生成订单所需的课程信息")
    @GetMapping("/getCourseInfoForOrder/{id}")
    public CourseWebVo getCourseInfoWeb(@ApiParam(name = "id", value = "课程 id", required = true) @PathVariable String id) {
        CourseWebVo courseInfo = eduCourseService.getCourseInfoWeb(id);
        return courseInfo;
    }

    @ApiOperation("获取当日新增的课程数")
    @GetMapping("/getNumberAddCourse/{date}")
    public int getNumberAddCourse(@ApiParam(name = "date", value = "日期", required = true) @PathVariable String date) {
        return eduCourseService.getNumberAddCourse(date);
    }

}
