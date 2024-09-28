package com.yuunik.eduservice.controller.front;

import com.yuunik.eduservice.service.EduCourseService;
import com.yuunik.eduservice.service.EduTeacherService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(description = "前端用户系统讲师接口")
@RestController
@RequestMapping("/eduservice/front-end/teacher")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("分页查询讲师信息")
    @GetMapping("/pageTeacherInfo/{current}/{pageSize}")
    public R pageTeacherInfo(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                             @ApiParam(name = "pageSize", value = "查询页数", required = true) @PathVariable long pageSize) {
        Map<String, Object> resultMap = eduTeacherService.pageTeacherInfo(current, pageSize);
        return R.ok().data(resultMap);
    }

    @ApiOperation("查询讲师详情")
    @GetMapping("/getTeacherInfo/{id}")
    public R getTeacherInfo(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        Map<String, Object> result = eduTeacherService.getTeacherFrontInfo(id);
        return R.ok().data(result);
    }
}
