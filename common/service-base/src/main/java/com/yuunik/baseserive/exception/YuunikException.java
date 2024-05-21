package com.yuunik.baseserive.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YuunikException extends RuntimeException{
    // 请求响应码
    private Integer code;

    // 错误信息
    private String msg;
}
