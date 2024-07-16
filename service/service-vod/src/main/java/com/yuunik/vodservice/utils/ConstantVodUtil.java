package com.yuunik.vodservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtil implements InitializingBean {
    // 阿里云公钥 id
    @Value("${aliyun.vod.file.access-key-id}")
    private String accessKeyId;

    // 阿里云公钥 key
    @Value("${aliyun.vod.file.access-key-secret}")
    private String accessKeySecret;

    // 阿里云公钥 id
    public static String ACCESS_KEY_ID;
    // 阿里云公钥 key
    public static String ACCESS_KEY_SECRET;

    // 初始化方法
    @Override
    public void afterPropertiesSet() throws Exception {
        // 赋值
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
    }
}
