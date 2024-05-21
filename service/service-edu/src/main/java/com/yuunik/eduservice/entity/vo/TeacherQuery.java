package com.yuunik.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("讲师条件查询对象")
public class TeacherQuery {
    @ApiModelProperty(value = "教师名称,模糊查询", example = "yuunik")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师", example = "1")
    private Integer level;

    //注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    @ApiModelProperty(value = "查询开始时间", example = "2024-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2024-12-01 10:10:10")
    private String end;
}
