package com.yuunik.orderservice.client;

import com.yuunik.utilscommon.orderVo.MemberWebVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户模块微服务客户端接口
 */
@FeignClient(name = "service-ucenter")
@Component
public interface UcenterClient {
    /**
     * 获取用户信息
     * @param id 用户id
     * @return
     */
    @GetMapping("/ucenterservice/member/getUserInfoWeb/{id}")
    MemberWebVo getUserInfoWeb(@PathVariable("id") String id);
}
