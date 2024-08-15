package com.yuunik.smsservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.smsservice.service.SmsService;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class SmsServiceImpl implements SmsService {

    // 发送短信验证码
    @Override
    public boolean sendSmsCode(String phoneNumber, Map<String, String> code) {
        // 配置基本信息
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5tBgjLQ7v7eX9vhLdaYR", "WbC7aXsRYMGnr02Z3gQiRPWubo1PCU");
        // 创建客户端
        IAcsClient client = new DefaultAcsClient(profile);
        // 获取请求
        CommonRequest request = new CommonRequest();
        // 设置固定参数
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        // 设置接受短信验证码的手机
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        // 设置签名名称
        request.putQueryParameter("SignName", "阿里云短信测试");
        // 设置模板名称
        request.putQueryParameter("TemplateCode", "SMS_154950909");
        // 设置发送的短信验证码
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));

        // 发送请求, 并获取响应信息
        try {
            CommonResponse response = client.getCommonResponse(request);
            // 获取是否成功的信息
            boolean isSuccess = response.getHttpResponse().isSuccess();
            System.out.println(new Gson().toJson(response));
            return isSuccess;
        } catch (ClientException e) {
            // 输出异常信息
            e.printStackTrace();
            // 抛出异常
            return false;
        }
    }
}
