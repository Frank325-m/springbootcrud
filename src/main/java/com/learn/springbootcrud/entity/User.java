package com.learn.springbootcrud.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data // Lombok注解，自动生成get/set/toString等方法
@TableName("user") // 对应数据库表名
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO) //主键自增
    private Long id;
    private String username;
    // private String password;
    private Integer age;
    // private String email;
    private LocalDateTime createTime; // 驼峰对应数据库create_time
}