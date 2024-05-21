package com.yuunik.baseserive.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 * @author yuunik
 * @since 1.0
 * @version 1.0
 */
@Data
// 生成无参数的构造方法
@NoArgsConstructor
// 生成有参数的构造方法
@AllArgsConstructor
public class YuunikException extends RuntimeException{
    // 请求响应码
    private Integer code;

    // 错误信息
    private String msg;
}
