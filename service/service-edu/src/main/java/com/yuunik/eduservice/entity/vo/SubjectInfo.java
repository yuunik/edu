package com.yuunik.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "课程分类对象", description = "用于新增课程信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectInfo {
    // 一级分类
    @ApiModelProperty("一级分类")
    private String oneSubject;

    // 二级分类
    @ApiModelProperty("二级分类")
    private String twoSubject;
}
