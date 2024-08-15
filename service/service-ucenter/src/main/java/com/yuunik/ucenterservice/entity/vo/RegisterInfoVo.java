package com.yuunik.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "注册信息", description = "注册信息对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInfoVo {
    @ApiModelProperty("用户手机号")
    private String mobile;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("注册验证码")
    private String code;
}
