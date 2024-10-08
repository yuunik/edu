package com.yuunik.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.client.OrderClient;
import com.yuunik.eduservice.entity.EduCourse;
import com.yuunik.eduservice.entity.EduCourseDescription;
import com.yuunik.eduservice.entity.EduTeacher;
import com.yuunik.eduservice.entity.chapter.ChapterVo;
import com.yuunik.eduservice.entity.front.CourseInfoVo;
import com.yuunik.eduservice.entity.subject.OneSubject;
import com.yuunik.eduservice.entity.vo.CourseInfoVO;
import com.yuunik.eduservice.entity.vo.CoursePublishVo;
import com.yuunik.eduservice.entity.vo.CourseQueryVo;
import com.yuunik.eduservice.mapper.EduCourseMapper;
import com.yuunik.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuunik.utilscommon.orderVo.CourseWebVo;
import com.yuunik.utilscommon.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduSubjectService eduSubjectService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private OrderClient orderClient;

    // 新增课程基本信息
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
        // 封装课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        // 封装课程简介信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVO, eduCourseDescription);
        // 调用接口， 新增课程信息
        boolean mainFlag = this.save(eduCourse);
        if (!mainFlag) {
            throw new YuunikException(20001, "新增课程失败");
        }
        // 设置课程简介信息的 id
        eduCourseDescription.setId(eduCourse.getId());
        // 调用接口， 新增课程简介信息
        eduCourseDescriptionService.save(eduCourseDescription);

        // 返回课程 id
        return eduCourse.getId();
    }

    // 根据课程 id 查询课程基本信息
    @Override
    public CourseInfoVO selectCourseInfo(String courseId) {
        // 响应数据
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        // 调用接口, 获取课程信息
        EduCourse eduCourse = this.getById(courseId);
        if (eduCourse == null) {
            throw new YuunikException(20001, "不存在该课程");
        }
        BeanUtils.copyProperties(eduCourse, courseInfoVO);
        // 调用接口, 获取课程简介信息
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("id", courseId);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getOne(eduCourseDescriptionQueryWrapper);
        if (eduCourseDescription == null) {
            throw new YuunikException(20001, "不存在该课程简介");
        }
        // 设置课程简介信息
        courseInfoVO.setDescription(eduCourseDescription.getDescription());

        return courseInfoVO;
    }

    // 修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        // 课程信息
        EduCourse eduCourse = new EduCourse();
        // 课程简介信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        // 获取课程信息
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        // 调用接口, 修改课程信息
        boolean isUpdateCourse = this.updateById(eduCourse);
        if (!isUpdateCourse) {
            // 抛出错误
            throw new YuunikException(20001, "修改课程信息错误");
        }
        // 获取课程信息
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("id", courseInfoVO.getId());
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        // 修改课程简介信息
        boolean isUpdateDescription = eduCourseDescriptionService.update(eduCourseDescription, eduCourseDescriptionQueryWrapper);
        if (!isUpdateDescription) {
            // 抛出错误
            throw new YuunikException(20001, "修改课程简介信息错误");
        }
    }

    // 根据课程 id 查询课程发布信息
    @Override
    public CoursePublishVo getCoursePublishInfo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishInfo(id);
        System.out.println(coursePublishVo);
        System.out.println("pause");
        return baseMapper.selectCoursePublishInfo(id);
    }

    // 发布课程
    @Override
    public boolean publishCourse(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        boolean isUpdate = this.updateById(eduCourse);
        if (isUpdate) {
            return true;
        }
        return false;
    }

    // 获取课程列表
    @Override
    public Map<String, Object> pageCourseList(long current, long pageSize, CourseQueryVo courseQueryVo) {
        // 构造条件
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        // 获取查询条件
        String title = courseQueryVo.getTitle();
        String status = courseQueryVo.getStatus();
        // 非空判断
        if (!StringUtils.isEmpty(title)) {
            eduCourseQueryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            eduCourseQueryWrapper.eq("status", status);
        }
        // 分页条件
        Page<EduCourse> eduCoursePage = new Page<>(current, pageSize);
        // 按创建时间降序排列
        eduCourseQueryWrapper.orderByDesc("gmt_create");
        // 调用接口，获取课程列表
        IPage<EduCourse> pageResult = this.page(eduCoursePage, eduCourseQueryWrapper);
        // 获取课程列表
        List<EduCourse> courseList = pageResult.getRecords();
        // 获取当前页
        long resultCurrent = pageResult.getCurrent();
        // 获取每页记录数
        long resultSize = pageResult.getSize();
        // 获取总页数
        long resultTotal = pageResult.getTotal();
        if (courseList == null) {
            // 抛出异常
            throw new YuunikException(20001, "获取课程列表失败");
        }
        // 根据响应, 封装响应数据
        Map<String, Object> eduCoursePageResult = new HashMap<>();
        eduCoursePageResult.put("current", resultCurrent);
        eduCoursePageResult.put("pageSize", resultSize);
        eduCoursePageResult.put("total", resultTotal);
        eduCoursePageResult.put("records", courseList);
        return eduCoursePageResult;
    }

    // 删除课程
    @Override
    public void removeCourse(String id) {
        // 删除课程小节
        eduVideoService.removeVideoByCourseId(id);
        // 删除课程章节
        eduChapterService.removeChapterByCourseId(id);
        // 删除课程描述
        eduCourseDescriptionService.removeCourseDescription(id);
        // 删除课程
        boolean result = this.removeById(id);
        if (!result) {
            // 删除课程失败
            throw new YuunikException(20001, "删除课程失败");
        }
    }

    // 获取8个最热门课程列表
    @Cacheable(value = "popularCourseList", key = "'SelectIndexCourseList'")
    @Override
    public List<EduCourse> getPopularCourseList() {
        // 构建条件
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        // 以观看人数降序排列
        eduCourseQueryWrapper.orderByDesc("view_count");
        // 取 8 条信息
        eduCourseQueryWrapper.last("limit 8");
        // 调用接口, 获取课程列表
        List<EduCourse> courseList = this.list(eduCourseQueryWrapper);
        if (courseList == null) {
            // 抛出异常
            throw new YuunikException(20001, "获取热门课程列表失败");
        }
        return courseList;
    }

    // 前台用户端条件获取课程列表信息及分类列表
    @Override
    public Map<String, Object> pageCourseListByCondition(long current, long pageSize, com.yuunik.eduservice.entity.front.CourseQueryVo courseQueryVo) {
        Page<EduCourse> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<EduCourse> wrapper = new QueryWrapper<EduCourse>().lambda();
        // 获取查询条件
        String courseName = "";
        String subjectId = "";
        String subjectParentId = "";
        String sort = "";
        // 非空校验
        if (courseQueryVo != null) {
            courseName = courseQueryVo.getCourseName();
            subjectId = courseQueryVo.getSubjectId();
            subjectParentId = courseQueryVo.getSubjectParentId();
            sort = courseQueryVo.getSort();
        }
        if (!StringUtils.isEmpty(courseName)) {
            // 课程名称模糊搜索
            wrapper.like(EduCourse::getTitle, courseName);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq(EduCourse::getSubjectParentId, subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq(EduCourse::getSubjectId, subjectId);
        }
        if (!StringUtils.isEmpty(sort)) {
            if ("price".equals(sort)) {
                wrapper.orderByDesc(EduCourse::getPrice);
            } else if ("buyCount".equals(sort)) {
                wrapper.orderByDesc(EduCourse::getBuyCount);
            } else if ("gmtCreate".equals(sort)) {
                wrapper.orderByDesc(EduCourse::getGmtCreate);
            }
        }
        // 调用接口, 获取课程列表
        this.page(page, wrapper);
        // 调用接口, 获取分类列表
        List<OneSubject> subjectList = eduSubjectService.getSubjectData();
        // 封装响应数据
        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("pages", page.getPages());
        result.put("current", page.getCurrent());
        result.put("pageSize", page.getSize());
        result.put("records", page.getRecords());
        result.put("hasPrevious", page.hasPrevious());
        result.put("hasNext", page.hasNext());
        result.put("courseList", page.getRecords());
        result.put("subjectList", subjectList);

        return result;
    }

    // 获取前台用户端课程详情
    @Override
    public Map<String, Object> getCourseFrontInfo(String id, HttpServletRequest request) {
        // 调用接口, 获取课程详情
        CourseInfoVo courseInfo = baseMapper.selectCourseFrontInfo(id);
        // 调用接口, 获取课程章节信息
        List<ChapterVo> chapterList = eduChapterService.getChapterList(id);
        // 获取用户的id
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        if (memberId == null) {
            // 未登录
            throw new YuunikException(20001, "请登录!");
        }
        // 微服务调用, 判断该课程是否被购买过
        boolean isBuy = orderClient.isBuyCourse(id, memberId);
        // 封装响应数据
        Map<String, Object> result = new HashMap<>();
        result.put("courseInfo", courseInfo);
        result.put("chapterList", chapterList);
        result.put("isBuy", isBuy);
        return result;
    }

    // 获取生成订单所需的课程信息
    @Override
    public CourseWebVo getCourseInfoWeb(String id) {
        // 条件
        LambdaQueryWrapper<EduCourse> wrapper = new QueryWrapper<EduCourse>().lambda();
        wrapper.eq(EduCourse::getId, id);
        // 调用接口, 查询相关课程信息
        EduCourse courseInfo = this.getOne(wrapper);
        // 非空判断
        if (courseInfo == null) {
            throw new YuunikException(20001, "暂无相关的课程信息");
        }
        // 讲师条件
        LambdaQueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<EduTeacher>().lambda();
        teacherWrapper.eq(EduTeacher::getId, courseInfo.getTeacherId());
        // 只获取讲师姓名
        teacherWrapper.select(EduTeacher::getName);
        // 调用接口, 查询讲师姓名
        EduTeacher teacherInfo = eduTeacherService.getOne(teacherWrapper);
        if (StringUtils.isEmpty(teacherInfo.getName())) {
            // 抛出异常
            throw new YuunikException(20001, "暂无相关的讲师信息");
        }
        // 根据响应结果, 封装响应数据
        CourseWebVo courseWebVo = new CourseWebVo();
        BeanUtils.copyProperties(courseInfo, courseWebVo);
        courseWebVo.setTeacherName(teacherInfo.getName());
        return courseWebVo;
    }

    // 获取当日新增课程数
    @Override
    public int getNumberAddCourse(String date) {
        return baseMapper.selectNumberAddCourse(date);
    }
}
