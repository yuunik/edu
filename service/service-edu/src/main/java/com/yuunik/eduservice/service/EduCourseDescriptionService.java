package com.yuunik.eduservice.service;

import com.yuunik.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void removeCourseDescription(String id);
}
