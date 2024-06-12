package com.yuunik.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yuunik.eduservice.entity.EduSubject;
import com.yuunik.eduservice.entity.excel.Subject;
import com.yuunik.eduservice.lisntener.SubjectExcelListener;
import com.yuunik.eduservice.mapper.EduSubjectMapper;
import com.yuunik.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;
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
}
