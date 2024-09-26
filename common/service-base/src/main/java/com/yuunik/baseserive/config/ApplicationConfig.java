package com.yuunik.baseserive.config;

import com.yuunik.baseserive.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
    // 做token校验
    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器, 拦截所有请求，除了登录和注册接口
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
    }*/
}
