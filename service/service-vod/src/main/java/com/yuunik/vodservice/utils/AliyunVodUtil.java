package com.yuunik.vodservice.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

// 初始化账号Client
public class AliyunVodUtil {
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws Exception {
        // 点播服务接入地域
        String regionId = "cn-shanghai";
        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
