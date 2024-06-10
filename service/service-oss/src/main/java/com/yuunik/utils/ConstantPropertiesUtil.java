package com.yuunik.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {
    // oss 地域节点
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    // 访问密钥 id
    @Value("${aliyun.oss.file.access-key-id}")
    private String accessKeyId;

    // 访问密钥
    @Value("${aliyun.oss.file.access-key-secret}")
    private String accessKeySecret;

    // bucket 名称
    @Value("${aliyun.oss.file.bucket-name}")
    private String bucketName;

    // 文件内容
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    // 读取配置文件内容
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketName;
    }
}
