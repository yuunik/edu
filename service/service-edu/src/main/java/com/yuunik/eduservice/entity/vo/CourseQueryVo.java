package com.yuunik.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("课程查询对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseQueryVo {
    @ApiModelProperty(value = "课程名称", example = "java")
    private String title;

    @ApiModelProperty(value = "课程状态", example = "Normal")
    private String status;
}
