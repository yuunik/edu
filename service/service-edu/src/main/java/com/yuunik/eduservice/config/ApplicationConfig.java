package com.yuunik.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置类
 */
@Configuration
@MapperScan(basePackages = "com.yuunik.eduservice.mapper")
public class ApplicationConfig {

}
