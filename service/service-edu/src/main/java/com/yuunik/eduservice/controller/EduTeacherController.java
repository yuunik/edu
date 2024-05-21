package com.yuunik.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.entity.vo.TeacherQuery;
import com.yuunik.eduservice.service.EduTeacherService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            map.put("records", eduTeacherPage.getRecords());

            return R.ok().data(map);
        } catch (Error error) {
            return R.error();
        }

    }

    @ApiOperation("根据条件分页查询讲师列表")
    @PostMapping("/pageTeacherListByCondition/{current}/{limit}")
    public R pageTeacherListByCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                                        @ApiParam(name = "limit", value = "每页条数", required = true) @PathVariable long limit,
                                        @ApiParam(name = "teacherQuery", value = "查询条件对象", required = false) @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 查询条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        // 分页条件
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        // 获取入参
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 非空判断
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_modified", end);
        }

        try {
            eduTeacherService.page(eduTeacherPage, queryWrapper);

            // 封装响应结果
            Map<String, Object> map = new HashMap<>();
            map.put("total", eduTeacherPage.getTotal());
            map.put("records", eduTeacherPage.getRecords());

            return R.ok().data(map);
        } catch (Error err) {
            return R.error();
        }
    }

    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@ApiParam(name = "teacherQuery", value = "查询条件对象", required = true) @RequestBody(required = true) EduTeacher eduTeacher) {
        boolean result = eduTeacherService.save(eduTeacher);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("根据 id 查询讲师")
    @GetMapping("/getTeacherById/{id}")
    public R getTeacherById(@ApiParam(name = "id", value = "讲师 id", required = true) @PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);

        if (eduTeacher == null) {
            return R.error();
        } else {
            return R.ok().data("eduTeacher", eduTeacher);
        }
    }

    @ApiOperation("修改讲师")
    @PostMapping("/editTeacher")
    public R editTeacher(@ApiParam(name = "eduTeacher", value = "讲师对象", required = true) @RequestBody EduTeacher eduTeacher) {
        System.out.println(eduTeacher);
        // 调用接口, 修改讲师
        boolean result = eduTeacherService.updateById(eduTeacher);
        // 判断
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

