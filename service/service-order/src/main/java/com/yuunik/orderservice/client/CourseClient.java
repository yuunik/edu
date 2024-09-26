package com.yuunik.orderservice.client;

import com.yuunik.utilscommon.orderVo.CourseWebVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 课程微服务客户端接口
 */
@FeignClient(name = "service-edu")
@Component
public interface CourseClient {
    /**
     * 获取课程信息
     * @param id 课程 id
     * @return 课程信息
     */
    @GetMapping("/eduservice/front-end/course/getCourseInfoForOrder/{id}")
    CourseWebVo getCourseInfoWeb(@PathVariable("id") String id);
}
