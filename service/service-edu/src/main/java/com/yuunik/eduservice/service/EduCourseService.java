package com.yuunik.eduservice.service;

import com.yuunik.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuunik.eduservice.entity.vo.CourseInfoVO;
import com.yuunik.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVO courseInfoVO);

    CourseInfoVO selectCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVO courseInfoVO);

    CoursePublishVo getCoursePublishInfo(String id);

    boolean publishCourse(String id);
}
