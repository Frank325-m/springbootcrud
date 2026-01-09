package com.learn.springbootcrud.service;

import java.util.List;

import com.learn.springbootcrud.entity.User;

public interface UserService {
    User getUserById(Long id);
    int updateUser(User user);
    int deleteUser(Long id);
    int addUser(User user);
    List<User> getUserList();
}
