package com.yuunik.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.eduservice.client.VodClient;
import com.yuunik.eduservice.entity.EduVideo;
import com.yuunik.eduservice.mapper.EduVideoMapper;
import com.yuunik.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-06-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    // 删除课程小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 根据课程id查询小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        Integer count = baseMapper.selectCount(eduVideoQueryWrapper);
        // 判断该课程 id 所属的小节个数
        if (count > 0) {
            List<EduVideo> eduVideoList = baseMapper.selectList(eduVideoQueryWrapper);
            // 要删除的课程视频id集合
            List<String> vodVideoIdList = new ArrayList<>();
            // 遍历小节集合, 获取小节视频id集合
            for (EduVideo eduVideo : eduVideoList) {
                String videoSourceId = eduVideo.getVideoSourceId();
                // 判断小节视频id是否为空
                if (!StringUtils.isEmpty(videoSourceId)) {
                    // 不为空时, 封装至要删除的课程视频id集合
                    vodVideoIdList.add(videoSourceId);
                }
            }
            // 若课程视频 id 集合不为空, 则批量删除课程视频
            if (!vodVideoIdList.isEmpty()) {
                // 微服务调用, 批量删除课程视频
                vodClient.batchDeleteVodVideo(vodVideoIdList);
            }
            // 批量删除小节
            boolean result = this.remove(eduVideoQueryWrapper);
            if (!result) {
                // 删除失败
                throw new YuunikException(20001, "删除小节失败");
            }
        }
    }
}
