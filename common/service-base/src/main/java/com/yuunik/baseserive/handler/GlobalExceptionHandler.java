package com.yuunik.baseserive.handler;

import com.yuunik.utilscommon.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
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
}
