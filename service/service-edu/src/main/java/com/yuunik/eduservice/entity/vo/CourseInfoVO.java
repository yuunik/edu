package com.yuunik.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "课程基本信息对象", description = "课程管理介绍")
@Data
public class CourseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程 id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty("讲师 id")
    private String teacherId;

    @ApiModelProperty("课程分类 id")
    private String subjectId;

    @ApiModelProperty("所属课程的一级分类 id")
    private String subjectParentId;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("课程价格")
    private BigDecimal price;

    @ApiModelProperty("课程总课时")
    private Integer lessonNum;

    @ApiModelProperty("课程封面")
    private String cover;

    @ApiModelProperty("购买人数")
    private Long buyCount;

    @ApiModelProperty("课程观看人数")
    private Long viewCount;

    @ApiModelProperty("课程简介")
    private String description;
}
