package com.yuunik.eduservice.service.impl;

import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduCourse;
import com.yuunik.eduservice.entity.EduCourseDescription;
import com.yuunik.eduservice.entity.vo.CourseInfoVO;
import com.yuunik.eduservice.mapper.EduCourseMapper;
import com.yuunik.eduservice.service.EduCourseDescriptionService;
import com.yuunik.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    // 新增课程基本信息
    @Override
    public void saveCourseInfo(CourseInfoVO courseInfoVO) {
        // 封装课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        // 封装课程简介信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVO, eduCourseDescription);
        // 调用接口， 新增课程信息
        boolean mainFlag = this.save(eduCourse);
        if (!mainFlag) {
            throw new YuunikException(20001, "新增课程失败");
        }
        // 设置课程简介信息的 id
        eduCourseDescription.setId(eduCourse.getId());
        // 调用接口， 新增课程简介信息
        eduCourseDescriptionService.save(eduCourseDescription);
    }
}
