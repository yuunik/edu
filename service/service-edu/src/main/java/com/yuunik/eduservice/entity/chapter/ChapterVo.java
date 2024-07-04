package com.yuunik.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "课程章节VO", description = "课程章节模块相关对象")
@Data
public class ChapterVo {
    @ApiModelProperty(value = "课程章节 id", required = true)
    private String key;

    @ApiModelProperty(value = "课程章节标题", required = true)
    private String title;

    @ApiModelProperty(value = "课程小节列表", required = true)
    private List<VideoVo> children;
}
