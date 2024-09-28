package com.yuunik.statisticsservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "图表数据对象", description = "图表数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartVo {
    @ApiModelProperty(value = "统计日期", required = true)
    private String date;

    @ApiModelProperty(value = "统计数据", required = true)
    private Integer num;
}
