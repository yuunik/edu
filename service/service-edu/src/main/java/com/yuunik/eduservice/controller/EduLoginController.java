package com.yuunik.eduservice.controller;

import com.yuunik.eduservice.entity.vo.LoginForm;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@Api(description = "用户登录接口")
@RestController
@RequestMapping("/eduservice/user")
// 解决跨域请求问题
@CrossOrigin
public class EduLoginController {

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public R login(@ApiParam(name = "username", value = "用户名", required = true) @RequestBody LoginForm loginForm) {
        System.out.println("loginForm = " + loginForm);
        return R.ok().data("token1111", "this is my first token.take it away");
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public R getUserInfo() {
        return R.ok().data("role", "[admin]").data("name", "yuunik").data("avatar", "https://avatars.githubusercontent.com/u/144108395?s=48&v=4");
    }
}
