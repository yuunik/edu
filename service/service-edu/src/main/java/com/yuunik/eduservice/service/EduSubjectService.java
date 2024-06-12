package com.yuunik.eduservice.service;

import com.yuunik.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-12
 */
public interface EduSubjectService extends IService<EduSubject> {

    void exportTemplate(HttpServletResponse response);

    void importSubjectData(MultipartFile file, EduSubjectService eduSubjectService);
}
