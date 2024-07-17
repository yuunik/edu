package com.yuunik.eduservice.service;

import com.yuunik.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);

    void removeVideoById(String id);
}
