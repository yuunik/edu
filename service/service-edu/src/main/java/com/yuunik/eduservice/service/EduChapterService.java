package com.yuunik.eduservice.service;

import com.yuunik.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuunik.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterList(String courseId);

    boolean removeChapter(String id);
}
