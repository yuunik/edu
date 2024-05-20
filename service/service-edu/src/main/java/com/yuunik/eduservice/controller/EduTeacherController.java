package com.yuunik.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.service.EduTeacherService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-05-20
 */
@Api(description = "讲师管理模块接口")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    private static final Logger log = LoggerFactory.getLogger(EduTeacherController.class);
    @Autowired
    private EduTeacherService eduTeacherService;

    // 查询所有的讲师列表
    @ApiOperation("查询所有的讲师列表")
    @GetMapping("/getTeacherInfoList")
    public R getTeacherInfoList() {
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return R.ok().data("teachList", teacherList);
    }

    // 根据 id 删除讲师
    @ApiOperation("根据 id 删除讲师")
    @DeleteMapping("/deleteTeacherById/{id}")
    public R deleteTeacherById(@ApiParam(name = "id", value = "讲师 id", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("分页查询讲师列表")
    @GetMapping("/pageTeacherList/{current}/{limit}")
    public R pageTeacherList(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                             @ApiParam(name = "limit", value = "每页条数", required = true) @PathVariable long limit) {
        try {
            Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
            eduTeacherService.page(eduTeacherPage, null);

            // 返回响应结果
            Map<String, Object> map = new HashMap<>();
            map.put("total", eduTeacherPage.getTotal());
            map.put("hasPrevious", eduTeacherPage.hasPrevious());
            map.put("hasNext", eduTeacherPage.hasNext());
            map.put("records", eduTeacherPage.getRecords());

            return R.ok().data(map);
        } catch (Error error) {
            return R.error();
        }

    }
}

