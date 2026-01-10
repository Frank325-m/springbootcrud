package com.learn.springbootcrud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springbootcrud.common.BusinessException;
import com.learn.springbootcrud.common.Result;
import com.learn.springbootcrud.entity.User;
import com.learn.springbootcrud.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

@Tag(name="用户管理", description = "用户的增删改查接口") // 接口分组标签
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    // 新增用户 -> 返回统一结果
    @Operation(summary = "新增用户", description = "传入用户名和年龄，新增用户信息") // 接口描述
    @PostMapping("/add")
    public Result<String> addUser(
        @Parameter(description = "用户姓名和年龄",required = true,example="{'username':'张三','age':25}")
        @RequestBody User user) {
        // 业务校验：用户名不为空
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new BusinessException("username is not null");
        }
        int count = userService.addUser(user);
        return count > 0 ? Result.success("New Account insert success, ID: " + user.getId()) : Result.error(400, "add user failed");
    }

    // 根据ID查询用户 -> 返回统一结果
    @Operation(summary = "根据ID查询用户", description="传入用户ID，查询单个用户信息")
    @GetMapping("/{id}")
    public Result<User> getUserById(
        @Parameter(description = "用户ID",required = true,example="1")
        @PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new BusinessException("User ID is nonexist, ID: " + id);
        }

        return Result.success(user);
    }

    // 查询所有用户 -> 返回统一结果
    @Operation(summary="查询所有用户",description="获取数据表中所有用户的信息")
    @GetMapping("/list")
    public Result<List<User>> listUser() {
        List<User> userList = userService.getUserList();
        return Result.success(userList);
    }

    //  修改用户 -> 返回统一结果
    @Operation(summary="更新用户信息", description="根据用户ID去更新用户的信息")
    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }
        int count = userService.updateUser(user);
        return count > 0 ? Result.success("modify user success") : Result.error(400, "modify user failed");
    }

    // 删除用户 -> 统一返回结果
    @Operation(summary="根据ID删除用户", description="根据用户ID，删除用户")
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteUser(
        @Parameter(description = "用户ID",required = true,example="1")
        @PathVariable Long id) {
        int count = userService.deleteUser(id);
        if (count == 0) {
            throw new BusinessException("删除失败，用户不存在，ID：" + id);
        }
        return count > 0 ? Result.success("delete user success") : Result.error(400, "delete user failed");
    }
}
