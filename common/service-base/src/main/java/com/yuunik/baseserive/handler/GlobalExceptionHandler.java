package com.yuunik.baseserive.handler;

import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.utilscommon.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 异常处理类
 */
@ControllerAdvice
@Slf4j
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
        /**
         * 输出错误相关信息
         */
        // 输出至日志文件
        log.error("全局异常处理: " + error.getMessage());
        // 输出至后台
        error.printStackTrace();
        return R.error().code(error.getCode()).message(error.getMsg());
    }

    /**
     * 讲师表单姓名重复错误捕获
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public R sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException error) {
        // 输出至日志文件
        log.error("讲师表单姓名重复错误捕获: " + error.getMessage());
        // 输出至后台
        error.printStackTrace();

        return R.error().message("讲师姓名重复, 请重新输入");
    }
}
