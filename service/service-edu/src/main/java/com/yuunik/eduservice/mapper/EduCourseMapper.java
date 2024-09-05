package com.yuunik.eduservice.mapper;

import com.yuunik.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuunik.eduservice.entity.front.CourseInfoVo;
import com.yuunik.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    // 获取课程发布信息
    CoursePublishVo selectCoursePublishInfo(String id);
    // 获取前台用户端课程详情信息
    CourseInfoVo selectCourseFrontInfo(String id);
}
