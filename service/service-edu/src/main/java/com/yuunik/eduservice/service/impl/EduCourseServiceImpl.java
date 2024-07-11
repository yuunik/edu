package com.yuunik.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduCourse;
import com.yuunik.eduservice.entity.EduCourseDescription;
import com.yuunik.eduservice.entity.vo.CourseInfoVO;
import com.yuunik.eduservice.entity.vo.CoursePublishVo;
import com.yuunik.eduservice.mapper.EduCourseMapper;
import com.yuunik.eduservice.service.EduCourseDescriptionService;
import com.yuunik.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
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

        // 返回课程 id
        return eduCourse.getId();
    }

    // 根据课程 id 查询课程基本信息
    @Override
    public CourseInfoVO selectCourseInfo(String courseId) {
        // 响应数据
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        // 调用接口, 获取课程信息
        EduCourse eduCourse = this.getById(courseId);
        if (eduCourse == null) {
            throw new YuunikException(20001, "不存在该课程");
        }
        BeanUtils.copyProperties(eduCourse, courseInfoVO);
        // 调用接口, 获取课程简介信息
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("id", courseId);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getOne(eduCourseDescriptionQueryWrapper);
        if (eduCourseDescription == null) {
            throw new YuunikException(20001, "不存在该课程简介");
        }
        // 设置课程简介信息
        courseInfoVO.setDescription(eduCourseDescription.getDescription());

        return courseInfoVO;
    }

    // 修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        // 课程信息
        EduCourse eduCourse = new EduCourse();
        // 课程简介信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        // 获取课程信息
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        // 调用接口, 修改课程信息
        boolean isUpdateCourse = this.updateById(eduCourse);
        if (!isUpdateCourse) {
            // 抛出错误
            throw new YuunikException(20001, "修改课程信息错误");
        }
        // 获取课程信息
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("id", courseInfoVO.getId());
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        // 修改课程简介信息
        boolean isUpdateDescription = eduCourseDescriptionService.update(eduCourseDescription, eduCourseDescriptionQueryWrapper);
        if (!isUpdateDescription) {
            // 抛出错误
            throw new YuunikException(20001, "修改课程简介信息错误");
        }
    }

    // 根据课程 id 查询课程发布信息
    @Override
    public CoursePublishVo getCoursePublishInfo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishInfo(id);
        System.out.println(coursePublishVo);
        System.out.println("pause");
        return baseMapper.selectCoursePublishInfo(id);
    }

    // 发布课程
    @Override
    public boolean publishCourse(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        boolean isUpdate = this.updateById(eduCourse);
        if (isUpdate) {
            return true;
        }
        return false;
    }

    // 获取课程列表
    @Override
    public List<EduCourse> getCourseList() {
        // 构造条件
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        // 按创建时间降序排列
        eduCourseQueryWrapper.orderByDesc("gmt_create");
        List<EduCourse> courseList = this.list(eduCourseQueryWrapper);
        if (courseList == null) {
            // 抛出异常
            throw new YuunikException(20001, "获取课程列表失败");
        }
        return courseList;
    }
}
