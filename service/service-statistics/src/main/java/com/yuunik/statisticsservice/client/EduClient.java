package com.yuunik.statisticsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-edu", fallback = EduClientBreaker.class)
@Component
public interface EduClient {
    @GetMapping("/eduservice/front-end/course/getNumberAddCourse/{date}")
    int getNumberAddCourse(@PathVariable String date);
}
