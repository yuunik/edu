package com.yuunik.eduservice.client;

import com.yuunik.utilscommon.R;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 服务调用 Feign 客户端
@org.springframework.cloud.openfeign.FeignClient("service-vod")
@Component
public interface VodClient {
    // 批量删除视频
    @DeleteMapping("/vodservice/video/batchDeleteVodVideo")
    public R batchDeleteVodVideo(@RequestParam("videoList") List<String> videoIdList);
}
