package com.yuunik.ucenterservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信开发配置常量
 */
@Component
public class WechatConstantUtil implements InitializingBean {
    // 微信开放平台 appId
    @Value("${wechat.open.app-id}")
    private String appId;

    // 微信开放平台 appSecret
    @Value("${wechat.open.app-secret}")
    private String appSecret;

    // 微信开放平台 回调地址
    @Value("${wechat.open.redirect-url}")
    private String redirectUrl;

    /**
     * 静态变量，用于存储常量值
     */
    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;
    /**
     * 在所有属性设置完成后执行此方法
     * 该方法用于执行一些初始化操作，确保所有属性已经被正确设置
     * @throws Exception 如果初始化过程中发生异常，可以抛出异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 执行属性设置后的初始化逻辑
        APP_ID = appId;
        APP_SECRET = appSecret;
        REDIRECT_URL = redirectUrl;
    }

}
