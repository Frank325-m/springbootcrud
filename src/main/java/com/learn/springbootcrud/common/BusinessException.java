package com.learn.springbootcrud.common;

/**
 * 自定义业务异常（如参数错误、数据不存在等）
 */
public class BusinessException extends RuntimeException {
    private Integer code; // 自定义状态码
    
    // 构造方法
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    // 快捷构造：（通用业务异常400）
    public BusinessException(String message) {
        this(400, message);
    }

    // getter 
    public Integer getCode() {
        return code;
    }
}
