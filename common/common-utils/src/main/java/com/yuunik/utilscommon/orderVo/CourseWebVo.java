package com.yuunik.utilscommon.orderVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel(value = "课程信息vo", description = "生成订单中所需的course信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseWebVo {
    @ApiModelProperty(value = "课程 id")
    private String id;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "课程封面地址")
    private String cover;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;
}
