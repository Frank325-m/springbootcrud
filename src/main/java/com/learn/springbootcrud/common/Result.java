package com.learn.springbootcrud.common;

import java.io.Serializable;

import lombok.Data;

/**
 * 统一返回结果类
 * 所有接口返回格式：{"code": 状态码, "msg": 提示信息, "data": 业务数据}
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    // 状态码：200成功、500系统错误、400业务异常
    private Integer code;
    // 提示信息
    private String msg;
    // 业务数据（泛型适配不同类型返回值）
    private T data;

    // 静态工具方法：成功（数据）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("Operation Success");
        result.setData(data);
        return result;
    }

    // 静态工具方法：成功（无数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 静态工具方法：失败
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
