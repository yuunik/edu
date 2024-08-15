package com.yuunik.ucenterservice.controller;


import com.yuunik.ucenterservice.entity.UcenterMember;
import com.yuunik.ucenterservice.entity.vo.RegisterInfoVo;
import com.yuunik.ucenterservice.service.UcenterMemberService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-08-15
 */
@Api(description = "用户登录接口")
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("用户登录")
    @PostMapping("/loginUser")
    public R login(@ApiParam(name = "ucenterMember", value = "用户登录信息", required = true) @RequestBody UcenterMember ucenterMember) {
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token", token);
    }

    @ApiOperation("注册用户")
    @PostMapping("/registerUser")
    public R register(@ApiParam(name = "registerInfo", value = "用户注册信息", required = true) @RequestBody RegisterInfoVo registerInfo) {
        ucenterMemberService.registerUser(registerInfo);
        return R.ok();
    }
}

