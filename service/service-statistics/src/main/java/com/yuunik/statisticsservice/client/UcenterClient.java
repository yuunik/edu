package com.yuunik.statisticsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter", fallback = UcenterClientBreaker.class)
@Component
public interface UcenterClient {
    // 获取当日注册用户人数
    @GetMapping("/ucenterservice/member/getNumberRegistered/{date}")
    int getNumberRegistered(@PathVariable("date") String date);
}
