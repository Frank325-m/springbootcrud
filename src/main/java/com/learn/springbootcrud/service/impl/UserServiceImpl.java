package com.learn.springbootcrud.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learn.springbootcrud.entity.User;
import com.learn.springbootcrud.mapper.UserMapper;
import com.learn.springbootcrud.service.UserService;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserMapper userMapper;

    @Cacheable(value="user", key="#id")
    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    @CacheEvict(value="user", key="#id")
    @Override
    public int deleteUser(Long id) {

        return userMapper.deleteById(id);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.selectList(Wrappers.emptyWrapper());
    }
}