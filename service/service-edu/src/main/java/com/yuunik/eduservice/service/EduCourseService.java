package com.yuunik.eduservice.service;

import com.yuunik.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuunik.eduservice.entity.vo.CourseInfoVO;
import com.yuunik.eduservice.entity.vo.CoursePublishVo;
import com.yuunik.eduservice.entity.vo.CourseQueryVo;

import java.util.List;
import java.util.Map;

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

    Map<String, Object> pageCourseList(long current, long pageSize, CourseQueryVo courseQueryVo);

    void removeCourse(String id);

    List<EduCourse> getPopularCourseList();

    Map<String, Object> pageCourseListByCondition(long current, long pageSize, com.yuunik.eduservice.entity.front.CourseQueryVo courseQueryVo);

    Map<String, Object> getCourseFrontInfo(String id);
}
