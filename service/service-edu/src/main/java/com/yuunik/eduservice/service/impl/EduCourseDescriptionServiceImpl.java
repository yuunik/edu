package com.yuunik.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduCourseDescription;
import com.yuunik.eduservice.mapper.EduCourseDescriptionMapper;
import com.yuunik.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    // 根据 id 删除课程简介
    @Override
    public void removeCourseDescription(String id) {
        // 查询是否存在课程简介
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("id", id);
        // 判断是否存在课程简介
        int count = this.count(eduCourseDescriptionQueryWrapper);
        if (count > 0) {
            // 删除课程简介
            boolean result = this.removeById(id);
            if (!result) {
                // 删除失败
                throw new YuunikException(20001, "删除课程简介失败");
            }
        }
    }
}
