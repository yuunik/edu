package com.yuunik.eduservice.entity.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "课程信息", description = "前台用户端课程详情封装")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程价格, 设置为 0 时则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private String lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "讲师资历, 一句话说明讲师")
    private String teacherIntro;

    @ApiModelProperty(value = "讲师头像")
    private String teacherAvatar;

    @ApiModelProperty(value = "课程一级分类id")
    private String subjectParentId;

    @ApiModelProperty(value = "课程二级分类id")
    private String subjectId;

    @ApiModelProperty(value = "课程一级分类名称")
    private String subjectParentName;

    @ApiModelProperty(value = "课程二级分类名称")
    private String subjectName;
}
