package com.yuunik.eduservice.controller;


import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // 根据 id 删除讲师
    @DeleteMapping("/deleteTeacherById/{id}")
    public boolean deleteTeacherById(@PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        return flag;
    }
}

