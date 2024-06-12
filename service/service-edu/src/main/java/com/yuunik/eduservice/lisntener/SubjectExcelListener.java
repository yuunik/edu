package com.yuunik.eduservice.lisntener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduSubject;
import com.yuunik.eduservice.entity.excel.Subject;
import com.yuunik.eduservice.service.EduSubjectService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// 无参构造器
@NoArgsConstructor
// 有参构造器
@AllArgsConstructor
public class SubjectExcelListener extends AnalysisEventListener<Subject> {
    private EduSubjectService eduSubjectService;

    // 读取文件
    @Override
    public void invoke(Subject subject, AnalysisContext analysisContext) {
        // 非空判断
        if (subject == null) {
            throw new YuunikException(20001, "文件数据为空");
        }
        // 查询一级分类是否存在
        EduSubject firstLevelSubject = existFirstLevelSubject(subject.getFirstSubject());
        // 若不存在
        if (firstLevelSubject == null) {
            firstLevelSubject = new EduSubject();
            firstLevelSubject.setTitle(subject.getFirstSubject());
            firstLevelSubject.setId("0");
            // 添加一级分类信息
            eduSubjectService.save(firstLevelSubject);
        }
        // 查询二级分类是否存在
        EduSubject secondLevelSubject = existSecondLevelSubject(subject.getSecondSubject(), firstLevelSubject.getId());
        if (secondLevelSubject == null) {
            secondLevelSubject = new EduSubject();
            secondLevelSubject.setTitle(subject.getSecondSubject());
            secondLevelSubject.setParentId(firstLevelSubject.getId());
            // 添加二级分类信息
            eduSubjectService.save(secondLevelSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    // 判断一级分类是否重复
    public EduSubject existFirstLevelSubject(String firstLevelSubject) {
        // 条件查询
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("title", firstLevelSubject);
        eduSubjectQueryWrapper.eq("parent_id", "0");
        // 查询一级分类是否存在
        EduSubject eduSubject = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return eduSubject;
    }

    // 判断二级分类是否重复
    public EduSubject existSecondLevelSubject(String secondLevelSubject, String pId) {
        // 查询二级分类是否存在
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("title", secondLevelSubject);
        eduSubjectQueryWrapper.eq("parent_id", pId);
        EduSubject eduSubject = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return eduSubject;
    }
}
