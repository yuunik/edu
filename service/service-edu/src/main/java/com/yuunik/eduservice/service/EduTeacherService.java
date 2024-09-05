package com.yuunik.eduservice.service;

import com.yuunik.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-05-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void exportTemplate(HttpServletResponse response);

    void importTeacherData(MultipartFile file, EduTeacherService eduTeacherService);

    List<EduTeacher> getFamousTeacherList();

    Map<String, Object> pageTeacherInfo(long current, long pageSize);

    Map<String, Object> getTeacherFrontInfo(String id);
}
