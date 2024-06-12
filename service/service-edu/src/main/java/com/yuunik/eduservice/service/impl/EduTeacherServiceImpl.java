package com.yuunik.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.entity.excel.Teacher;
import com.yuunik.eduservice.lisntener.TeacherExcelListener;
import com.yuunik.eduservice.mapper.EduTeacherMapper;
import com.yuunik.eduservice.service.EduTeacherService;
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
 * 讲师 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-05-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    // 下载模板
    @Override
    public void exportTemplate(HttpServletResponse response) {
        try {
            // 设置响应内容格式
            response.setContentType("application/vnd.ms-excel");
            // 设置响应编码集
            response.setCharacterEncoding("utf-8");
            // 防止中文乱码
            String fileName = URLEncoder.encode("新增讲师模板", "UTF-8");
            // 设置响应头
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 设置模板

            // 表头样式
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            // 表头字体样式
            WriteFont headWriteFont = new WriteFont();
            // 设置字体颜色
            headWriteFont.setColor(IndexedColors.WHITE.getIndex());
            headWriteCellStyle.setWriteFont(headWriteFont);

            // 内容样式
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

            // 模板内容
            EduTeacher eduTeacher = new EduTeacher();
            List<EduTeacher> eduTeacherList = new ArrayList<>();
            eduTeacherList.add(eduTeacher);

            // 设置模板样式
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

            // 导出模板
            EasyExcel.write(response.getOutputStream(), EduTeacher.class).registerWriteHandler(horizontalCellStyleStrategy).sheet("新增列表").doWrite(eduTeacherList);
        } catch (Exception e) {
            // 输出异常
            e.printStackTrace();
        }
    }

    // 导入模板
    @Override
    public void importTeacherData(MultipartFile file, EduTeacherService eduTeacherService) {
        try {
            // 读取文件输入流
            InputStream inputStream = file.getInputStream();
            // 读取文件
            EasyExcel.read(inputStream, Teacher.class, new TeacherExcelListener(eduTeacherService)).sheet().doRead();
        } catch (Exception e) {
            // 输出异常
            e.printStackTrace();
        }

    }
}
