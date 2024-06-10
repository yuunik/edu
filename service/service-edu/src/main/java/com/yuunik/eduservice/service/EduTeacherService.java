package com.yuunik.eduservice.service;

import com.yuunik.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-05-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void exportTemplate(HttpServletResponse response);
}
