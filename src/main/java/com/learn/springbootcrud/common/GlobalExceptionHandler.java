package com.learn.springbootcrud.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody,处理所有Controller的异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(),e.getMessage());
    }

    // 处理系统异常（如空指针、数据库异常等）
    @ExceptionHandler(Exception.class)
    public Result<?> handleSystemException(Exception e) {
        // 生产环境建议打印日志，不返回具体异常信息
        e.printStackTrace();
        return Result.error(500, "System Exception, Please contact the Administor");
    }
}
