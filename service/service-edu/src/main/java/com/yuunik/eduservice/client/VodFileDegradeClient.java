package com.yuunik.eduservice.client;

import com.yuunik.utilscommon.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeClient implements VodClient{
    @Override
    public R deleteVodVideo(String videoId) {
        return R.error().message("删除视频错误了, 熔断器启动");
    }

    @Override
    public R batchDeleteVodVideo(List<String> videoIdList) {
        return R.error().message("批量删除视频错误了, 熔断器启动");
    }
}
