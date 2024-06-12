package com.yuunik.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

@Data
// 设置导出模板的表头行高
@HeadRowHeight(20)
// 设置导出模板的列宽
@ColumnWidth(20)
public class Subject {
    // 一级分类
    @ExcelProperty(value = "一级分类", index = 0)
    private String firstSubject;

    // 二级分类
    @ExcelProperty(value = "二级分类", index = 1)
    private String secondSubject;
}
