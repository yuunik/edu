package com.yuunik.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduCourse;
import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.entity.excel.Teacher;
import com.yuunik.eduservice.lisntener.TeacherExcelListener;
import com.yuunik.eduservice.mapper.EduTeacherMapper;
import com.yuunik.eduservice.service.EduCourseService;
import com.yuunik.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

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

    @Autowired
    private EduCourseService eduCourseService;

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

    // 获取最受欢迎的 4 位讲师
    @Cacheable(value = "famousTeacherList", key = "'SelectIndexTeachetList'")
    @Override
    public List<EduTeacher> getFamousTeacherList() {
        // 构建条件
        LambdaQueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<EduTeacher>().lambda();
        // 排序
        queryWrapper.orderByDesc(EduTeacher::getSort).last("limit 4");
        // 查询
        List<EduTeacher> teacherList = baseMapper.selectList(queryWrapper);
        if (teacherList == null) {
            // 输出异常
            throw new YuunikException(20001, "获取讲师失败");
        }
        return teacherList;
    }

    // 前端页面查询讲师信息
    @Override
    public Map<String, Object> pageTeacherInfo(long current, long pageSize) {
        Page<EduTeacher> page = new Page<>(current, pageSize);
        // 查询条件
        LambdaQueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>().lambda();
        // 按创建时间 降序排序
        wrapper.orderByDesc(EduTeacher::getGmtCreate);
        // 调用接口, 查询数据
        this.page(page, wrapper);

        // 封装响应参数
        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("pages", page.getPages());
        result.put("current", page.getCurrent());
        result.put("pageSize", page.getSize());
        result.put("records", page.getRecords());
        result.put("hasPrevious", page.hasPrevious());
        result.put("hasNext", page.hasNext());
        return result;
    }

    // 前台用户页面查询讲师信息
    @Override
    public Map<String, Object> getTeacherFrontInfo(String id) {
        // 调用接口, 获取讲师信息
        EduTeacher teacher = this.getById(id);
        if (teacher == null) {
            // 抛出异常
            throw new YuunikException(20001, "获取讲师信息失败");
        }
        // 调用接口, 获取讲师所讲的课程信息
        LambdaQueryWrapper<EduCourse> wrapper = new QueryWrapper<EduCourse>().lambda();
        wrapper.eq(EduCourse::getTeacherId, id);
        List<EduCourse> courseList = eduCourseService.list(wrapper);

        // 封装响应参数
        Map<String, Object> result = new HashMap<>();
        result.put("teacher", teacher);
        result.put("courseList", courseList);
        return result;
    }
}
