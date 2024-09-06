package com.yuunik.ucenterservice.controller;

import com.yuunik.ucenterservice.service.UcenterMemberService;
import com.yuunik.ucenterservice.utils.WechatConstantUtil;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;

@Api(description = "微信登录接口")
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WechatController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("获取微信登录二维码")
    @GetMapping("/getQRCode/{state}")
    @ResponseBody
    public R getQRCode(@ApiParam(name = "state", value = "微信回调地址", required = true) @PathVariable String state) {
        try {

            // 微信开放平台授权baseUrl
            String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                    "?appid=%s" +
                    "&redirect_uri=%s" +
                    "&response_type=code" +
                    "&scope=snsapi_login" +
                    "&state=%s" +
                    "#wechat_redirect";

            // URL编码
            String redirectUrl = URLEncoder.encode(WechatConstantUtil.REDIRECT_URL, "UTF-8");
            // 拼接最终地址
            String url = String.format(baseUrl, WechatConstantUtil.APP_ID, redirectUrl, state);

            return R.ok().data("QRCodeUrl", url);
        } catch (Exception e) {
            // 输出异常信息
            e.printStackTrace();
            return R.error().message("获取微信登录二维码失败");
        }
    }

    @ApiOperation("微信登录回调接口")
    @GetMapping("/callback")
    public String callback(String code, String state) {
        String token = ucenterMemberService.loginByWechat(code, state);
        return "redirect:http://"+ URLDecoder.decode(state) +"?token=" + token;
    }
}
