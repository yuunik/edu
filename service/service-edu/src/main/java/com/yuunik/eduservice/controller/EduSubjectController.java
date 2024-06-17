package com.yuunik.eduservice.controller;


import com.yuunik.eduservice.entity.subject.OneSubject;
import com.yuunik.eduservice.entity.vo.SubjectInfo;
import com.yuunik.eduservice.service.EduSubjectService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-06-12
 */
@Api(description = "课程分类管理接口")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @ApiOperation("导出课程分类模板")
    @GetMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        try {
            eduSubjectService.exportTemplate(response);
        } catch (Exception e) {
            // 输出异常
            e.printStackTrace();
        }
    }

    @ApiOperation("导入课程分类文件")
    @PostMapping("/import")
    public R importSubjectData(@ApiParam(name = "file", value = "课程分类文件", required = true)MultipartFile file) {
        try {
            // 调用接口, 导入课程分类文件
            eduSubjectService.importSubjectData(file, eduSubjectService);
            return R.ok();
        } catch (Exception e) {
            // 输出异常
            e.printStackTrace();
            return R.error().message("导入课程分类模板失败");
        }
    }

    @ApiOperation("获取课程分类列表")
    @GetMapping("/getSubjectList")
    public R getSubjectData() {
        List<OneSubject> oneSubjectList = eduSubjectService.getSubjectData();
        return R.ok().data("subjectList", oneSubjectList);
    }

    @ApiOperation("添加课程分类信息")
    @PostMapping("/addSubject")
    public R addSubject(@ApiParam(name = "oneSubject", value = "一级课程分类", required = true) @RequestBody SubjectInfo subjectInfo) {
        eduSubjectService.addSubject(subjectInfo);
        return R.ok();
    }

}

