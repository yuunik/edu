package com.yuunik.eduservice.lisntener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.entity.excel.Teacher;
import com.yuunik.eduservice.service.EduTeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelListener extends AnalysisEventListener<Teacher> {
    private static final Logger log = LoggerFactory.getLogger(ExcelListener.class);
    private EduTeacherService eduTeacherService;

    // 有参构造器
    public ExcelListener(EduTeacherService eduTeacherService) {
        this.eduTeacherService = eduTeacherService;
    }

    // 读取数据
    @Override
    public void invoke(Teacher teacher, AnalysisContext analysisContext) {
        System.out.println("读取到数据：" + teacher);
        if (teacher == null) {
            // 抛出异常
            throw new YuunikException(20001, "数据不能为空");
        }
        // 处理数据
        String name = teacher.getName();
        Integer level = teacher.getLevel();
        Integer sort = teacher.getSort();
        String avatar = teacher.getAvatar();
        String intro = teacher.getIntro();
        String career = teacher.getCareer();

        // 封装数据
        EduTeacher eduTeacher = new EduTeacher();
        eduTeacher.setName(name);
        eduTeacher.setLevel(level);
        eduTeacher.setSort(sort);
        eduTeacher.setAvatar(avatar);
        eduTeacher.setIntro(intro);
        eduTeacher.setCareer(career);
        // 调用接口, 添加讲师
        eduTeacherService.save(eduTeacher);

    }

    // 读取完成
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
