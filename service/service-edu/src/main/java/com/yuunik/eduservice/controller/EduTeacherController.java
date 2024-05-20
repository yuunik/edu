package com.yuunik.eduservice.controller;


import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    // 查询所有的讲师列表
    @GetMapping("/getTeacherInfoList")
    public List<EduTeacher> getTeacherInfoList() {
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return teacherList;
    }
}

