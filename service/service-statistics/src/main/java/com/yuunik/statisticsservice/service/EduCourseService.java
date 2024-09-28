package com.yuunik.statisticsservice.service;

import com.yuunik.statisticsservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-29
 */
public interface EduCourseService extends IService<EduCourse> {
    int getNumberAddCourse(String date);
}
