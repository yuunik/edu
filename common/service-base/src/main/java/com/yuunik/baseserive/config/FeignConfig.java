package com.yuunik.baseserive.config;

import com.yuunik.baseserive.interceptor.RequestHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    /* Feign 配置, 可做 token 令牌中继 */
    /*@Bean
    public RequestHeaderInterceptor feignRequestHeaderInterceptor() {
        return new RequestHeaderInterceptor();
    }*/
}
