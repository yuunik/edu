package com.yuunik.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.entity.EduChapter;
import com.yuunik.eduservice.entity.EduVideo;
import com.yuunik.eduservice.entity.chapter.ChapterVo;
import com.yuunik.eduservice.entity.chapter.VideoVo;
import com.yuunik.eduservice.mapper.EduChapterMapper;
import com.yuunik.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuunik.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    // 获取课程章节列表
    @Override
    public List<ChapterVo> getChapterList(String courseId) {
        // 调用接口, 获取课程章节列表
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(eduChapterQueryWrapper);

        // 调用接口, 获取课程小节列表
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoQueryWrapper);

        // 返回的响应数据
        List<ChapterVo> chapterVoList = new ArrayList<>();

        // 遍历课程章节列表
        // 课程章节对象
        ChapterVo chapterVo = null;
        // 课程小节对象
        VideoVo videoVo = null;
        // 课程小节列表
        List<VideoVo> videoVoList = null;
        for (EduChapter eduChapter: eduChapterList) {
            // 创建章节对象
            chapterVo = new ChapterVo();
            chapterVo.setKey(eduChapter.getId());
            chapterVo.setTitle(eduChapter.getTitle());
            // BeanUtils.copyProperties(eduChapter, chapterVo);

            videoVoList = new ArrayList<>();
            // 遍历课程小节列表
            for (EduVideo eduVideo: eduVideoList) {
                videoVo = new VideoVo();

                // 判断小节所属的章节
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    // 封装 videoVo
                    videoVo.setKey(eduVideo.getId());
                    videoVo.setTitle(eduVideo.getTitle());
                    // BeanUtils.copyProperties(eduVideo, videoVo);
                    // 封装课程小节列表
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            // 封装章节列表
            chapterVoList.add(chapterVo);
        }

        return chapterVoList;
    }

    // 删除课程章节
    @Override
    public boolean removeChapter(String id) {
        // 查询条件
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id", id);
        int count = eduVideoService.count(eduVideoQueryWrapper);
        if (count != 0 ) {
            // 删除的课程章节下存在小节, 不允许删除
            throw new YuunikException(20001, "只能删除没有小节的课程章节");
        }
        // 调用接口, 删除课程章节
        int result = baseMapper.deleteById(id);
        // 根据删除的结果, 返回成功或失败的响应
        return result > 0;
    }
}
