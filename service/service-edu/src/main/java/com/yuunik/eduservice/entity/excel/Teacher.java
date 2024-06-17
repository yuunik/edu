package com.yuunik.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "新增讲师文件模板", description = "新增讲师文件模板")
@Data
public class Teacher {
    @ApiModelProperty(value = "讲师姓名")
    @ExcelProperty(value = "讲师姓名", index = 0)
    private String name;

    @ApiModelProperty(value = "讲师简介")
    @ExcelProperty(value = "讲师简介", index = 1)
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    @ExcelProperty(value = "讲师资历", index = 2)
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    @ExcelProperty(value = "头衔", index = 3)
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    @ExcelProperty(value = "讲师头像", index = 4)
    private String avatar;

    @ApiModelProperty(value = "排序")
    @ExcelProperty(value = "排序", index = 5)
    private Integer sort;
}
