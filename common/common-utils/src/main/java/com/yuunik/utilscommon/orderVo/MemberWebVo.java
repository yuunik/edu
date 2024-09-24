package com.yuunik.utilscommon.orderVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "用户信息vo", description = "生成订单所需的会员信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberWebVo {
    @ApiModelProperty(value = "用户 id")
    private String id;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户手机号码")
    private String mobile;
}
