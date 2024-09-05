package com.yuunik.eduservice.entity.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "课程查询对象", description = "课程查询对象封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseQueryVo {
    @ApiModelProperty("一级分类id")
    private String subjectParentId;

    @ApiModelProperty("二级分类id")
    private String subjectId;

    @ApiModelProperty("排序项")
    private String sort;
}
