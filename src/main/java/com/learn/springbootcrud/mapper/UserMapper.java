package com.learn.springbootcrud.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.springbootcrud.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
}
