package com.yuunik.baseserive.handler;

import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.utilscommon.R;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param error 错误对象
     * @return 统一返回错误信息
     */
    // 处理所有类型的错误
    @ExceptionHandler(Exception.class)
    // 返回 JSON 格式数据
    @ResponseBody
    public R globalExceptionHandler(Exception error) {
        error.printStackTrace();
        return R.error().message("请求错误, 请联系管理员...");
    }

    /**
     * 特定异常处理
     * @param error 请求方式错误对象
     *              处理网络请求方式错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public R httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException error) {
        // 输出错误信息
        error.printStackTrace();
        return R.error().message("请求方式错误, 请更换请求方式...");
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(YuunikException.class)
    @ResponseBody
    public R yuunikExceptionHandler(YuunikException error) {
        // 输出错误信息
        error.printStackTrace();
        return R.error().code(error.getCode()).message(error.getMsg());
    }
}
