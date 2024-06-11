package com.yuunik.eduservice.controller;


import com.yuunik.eduservice.service.EduSubjectService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-06-12
 */
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

}

