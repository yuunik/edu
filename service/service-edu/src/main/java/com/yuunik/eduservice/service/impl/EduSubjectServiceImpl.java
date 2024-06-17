package com.yuunik.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduSubject;
import com.yuunik.eduservice.entity.excel.Subject;
import com.yuunik.eduservice.entity.subject.OneSubject;
import com.yuunik.eduservice.entity.subject.TwoSubject;
import com.yuunik.eduservice.entity.vo.SubjectInfo;
import com.yuunik.eduservice.lisntener.SubjectExcelListener;
import com.yuunik.eduservice.mapper.EduSubjectMapper;
import com.yuunik.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-12
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 导出课程分类模板
    @Override
    public void exportTemplate(HttpServletResponse response) {


        // 设置响应的内容类型
        response.setContentType("application/vnd.ms-excel");
        // 设置响应的字符集
        response.setCharacterEncoding("UTF-8");

        // 防止模板标题导致的乱码
        try {
            String fileName = URLEncoder.encode("课程分类模板", "UTF-8");
            // 设置响应头
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

            // 设置样式相关
            // 设置表头样式
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            // 设置表头字体样式
            WriteFont headWriteFont = new WriteFont();
            // 设置表头字体为 白色
            headWriteFont.setColor(IndexedColors.WHITE.getIndex());
            headWriteCellStyle.setWriteFont(headWriteFont);

            // 设置内容样式
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

            // 设置样式
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

            // 空白模板
            Subject subject = new Subject();
            List<Subject> subjectList = new ArrayList<>();
            subjectList.add(subject);

            // 导出模板
            EasyExcel.write(response.getOutputStream(),     Subject.class).registerWriteHandler(horizontalCellStyleStrategy).sheet().doWrite(subjectList);
        } catch (Exception e) {
            // 输出异常
            e.printStackTrace();
        }


    }

    // 导入课程分类文件
    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 读取文件
            EasyExcel.read(inputStream, Subject.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            // 输出异常
            e.printStackTrace();
        }
    }

    // 获取课程管理列表
    @Override
    public List<OneSubject> getSubjectData() {
        // 获取一级分类列表
        // 条件查询
        QueryWrapper<EduSubject> oneSubjectQueryWrapper = new QueryWrapper<>();
        oneSubjectQueryWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneSubjectQueryWrapper);

        // 获取二级分类
        QueryWrapper<EduSubject> twoSubjectQueryWrapper = new QueryWrapper<>();
        twoSubjectQueryWrapper.ne("parent_id", "0");
        List<EduSubject> twoEduSubjectList = this.list(twoSubjectQueryWrapper);

        // 返回数据
        List<OneSubject> resultSubjectList = new ArrayList<>();
        // 一级分类
        OneSubject oneSubject = null;
        // 二级分类
        TwoSubject twoSubject = null;
        for (EduSubject oneEduSubject: oneSubjectList) {
            // 封装一级分类
            oneSubject = new OneSubject();
            oneSubject.setKey(oneEduSubject.getId());
            oneSubject.setTitle(oneEduSubject.getTitle());

            // 二级分类集合
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (EduSubject twoEduSubject: twoEduSubjectList) {
                // 判断二级分类id是否等于一级分类id
                if (twoEduSubject.getParentId().equals(oneEduSubject.getId())) {
                    // 封装二级分类
                    twoSubject = new TwoSubject();
                    twoSubject.setKey(twoEduSubject.getId());
                    twoSubject.setTitle(twoEduSubject.getTitle());
                    // 封装二级分类集合
                    twoSubjectList.add(twoSubject);
                }
            }
            // 添加一级分类的子集合
            oneSubject.setChildren(twoSubjectList);
            // 添加一级分类
            resultSubjectList.add(oneSubject);
        }
        // 返回数据
        return resultSubjectList;
    }

    // 添加课程分类信息
    @Override
    public void addSubject(SubjectInfo subjectInfo) {
        // 获取分类信息
        String oneSubject = subjectInfo.getOneSubject();
        String twoSubject = subjectInfo.getTwoSubject();
        // 非空判断
        if (StringUtils.isEmpty(oneSubject)) {
            throw new YuunikException(20001, "一级分类不能为空");
        }
        // 添加一级分类
        EduSubject oneEduSubject = new EduSubject();
        oneEduSubject.setTitle(oneSubject);
        oneEduSubject.setParentId("0");
        this.save(oneEduSubject);
        // 若二级分类存在， 则添加二级分类
        if (!Strings.isEmpty(twoSubject)) {
            EduSubject twoEduSubject = new EduSubject();
            twoEduSubject.setTitle(twoSubject);
            // 获取本次一级分类id
            QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
            eduSubjectQueryWrapper.eq("title", oneSubject);
            eduSubjectQueryWrapper.eq("parent_id", "0");
            // 获取一级分类id
            EduSubject resultEduSubject = baseMapper.selectOne(eduSubjectQueryWrapper);
            // 设置二级分类id
            twoEduSubject.setParentId(resultEduSubject.getId());
            // 添加二级分类
            baseMapper.insert(twoEduSubject);
        }
    }
}
