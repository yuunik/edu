package com.yuunik.orderservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeChatPayConstantsUtil implements InitializingBean {
    // 公众号 appId
    @Value("${wechat.pay.app-id}")
    private String appId;

    // 商户号
    @Value("${wechat.pay.partner-id}")
    private String partnerId;

    // 商户密钥
    @Value("${wechat.pay.partner-key}")
    private String partnerKey;

    // 回调地址
    @Value("${wechat.pay.notify-url}")
    private String notifyUrl;

    // 支付二维码请求域名
    @Value("${wechat.pay.domain}")
    private String domain;

    // 查询支付状态路径
    @Value("${wechat.pay.query-order-domain}")
    private String queryOrderDomain;

    // 微信支付常量
    public static String APP_ID;
    public static String PARTNER_ID;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;
    public static String DOMAIN;
    public static String QUERY_ORDER_DOMAIN;

    // 初始化
    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appId;
        PARTNER_ID = partnerId;
        PARTNER_KEY = partnerKey;
        NOTIFY_URL = notifyUrl;
        DOMAIN = domain;
        QUERY_ORDER_DOMAIN = queryOrderDomain;
    }
}
