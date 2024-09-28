package com.yuunik.statisticsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.statisticsservice.entity.EduCourse;
import com.yuunik.statisticsservice.mapper.EduCourseMapper;
import com.yuunik.statisticsservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.management.Query;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    // 获取当日新增的课程数
    @Override
    public int getNumberAddCourse(String date) {
        // 调用接口, 返回新增课程数
        return baseMapper.selectNumberAddCourse(date);
    }
}
