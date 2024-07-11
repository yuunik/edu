package com.yuunik.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "课程发布信息Vo", description = "课程发布信息Vo")
@Data
public class CoursePublishVo {
    @ApiModelProperty(value = "发布课程的 id")
    private String id;

    @ApiModelProperty(value = "发布课程的标题")
    private String title;

    @ApiModelProperty(value = "发布课程的封面")
    private String cover;

    @ApiModelProperty(value = "发布课程的总课时数")
    private Integer lessonNum;

    @ApiModelProperty(value = "发布课程的简介")
    private String description;

    @ApiModelProperty(value = "发布课程的一级分类")
    private String oneSubject;

    @ApiModelProperty(value = "发布课程的二级分类")
    private String twoSubject;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "讲师头像")
    private String teacherAvatar;

    @ApiModelProperty(value = "讲师简介")
    private String teacherIntro;

    @ApiModelProperty(value = "发布课程的售价")
    private String price;
}
