package com.yuunik.smsservice.controller;

import com.yuunik.smsservice.service.SmsService;
import com.yuunik.smsservice.utils.RandomUtil;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Api(description = "阿里云短信服务接口")
@RestController
@RequestMapping("/smsservice/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("发送短信验证码")
    @GetMapping("/send/{phoneNumber}")
    public R sendSmsCode(@ApiParam(name = "phoneNumber", value = "手机号", required = true) @PathVariable String phoneNumber) {
        // 手机号码非空判断
        if (StringUtils.isEmpty(phoneNumber)) {
            return R.error().message("手机号不能为空");
        }
        // 获取短信验证码
        String smsCode = redisTemplate.opsForValue().get(phoneNumber);
        if (!StringUtils.isEmpty(smsCode)) {
            return R.ok();
        }
        // 短信验证码已过期, 重新发送
        smsCode = RandomUtil.getSixBitRandom();
        Map<String, String> code = new HashMap<>();
        code.put("code", smsCode);
        // 调用接口, 发送短信验证码

        boolean isSend = false;
        try {
            isSend = smsService.sendSmsCode(phoneNumber, code);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (isSend) {
            // 发送成功
            // 存入 redis, 有效时间为 10 分钟
            redisTemplate.opsForValue().set(phoneNumber, smsCode, 10, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("验证码发送失败");
        }
    }

}
