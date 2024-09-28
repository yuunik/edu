package com.yuunik.statisticsservice.mapper;

import com.yuunik.statisticsservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yuunik
 * @since 2024-09-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    // 统计当日的新增课程数
    int selectNumberAddCourse(String date);
}
