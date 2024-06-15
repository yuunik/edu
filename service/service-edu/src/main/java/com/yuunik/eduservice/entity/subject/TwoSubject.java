package com.yuunik.eduservice.entity.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "二级分类对象", description = "课程管理对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoSubject {
    @ApiModelProperty(value = "二级分类 id")
    private String key;

    @ApiModelProperty(value = "二级分类名称")
    private String title;
}
