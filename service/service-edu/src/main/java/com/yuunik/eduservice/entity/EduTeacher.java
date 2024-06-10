package com.yuunik.eduservice.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author yuunik
 * @since 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduTeacher对象", description="讲师")
// 设置导出讲师模板的列宽
@ColumnWidth(25)
// 设置表头的行高
@HeadRowHeight(20)
public class EduTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @ExcelIgnore
    private String id;

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

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @ExcelIgnore
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @ExcelIgnore
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ExcelIgnore
    private Date gmtModified;


}
