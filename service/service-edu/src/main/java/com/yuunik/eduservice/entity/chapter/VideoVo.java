package com.yuunik.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "小节信息对象", description = "课程章节模块相关对象")
@Data
public class VideoVo {
    @ApiModelProperty(value = "课程小节 id")
    private String key;

    @ApiModelProperty(value = "课程小节标题")
    private String title;

    @ApiModelProperty(value = "课程小节阿里云视频点播id")
    private String videoSourceId;
}
