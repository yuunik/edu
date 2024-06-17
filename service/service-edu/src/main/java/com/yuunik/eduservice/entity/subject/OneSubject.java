package com.yuunik.eduservice.entity.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value = "一级分类对象", description = "课程分类管理对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneSubject {
    // 一级分类 id
    @ApiModelProperty(value = "一级分类 id")
    private String key;

    // 一级分类名称
    @ApiModelProperty(value = "一级分类名称")
    private String title;

    // 二级分类集合
    @ApiModelProperty(value = "二级分类集合")
    private List<TwoSubject> children;
}
