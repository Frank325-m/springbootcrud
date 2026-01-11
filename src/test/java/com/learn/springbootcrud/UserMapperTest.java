package com.learn.springbootcrud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.learn.springbootcrud.entity.User;
import com.learn.springbootcrud.mapper.UserMapper;

import jakarta.annotation.Resource;

/**
 * UserMapper单元测试
 * @SpringBootTest: 启动Spring容器，支持依赖注入
 */
@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    // 测试新增用户
    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("测试用户");
        user.setAge(25);
        int count = userMapper.insert(user);
        // 断言：新增成功返回1
        Assertions.assertEquals(1, count);
        // 断言：用户ID不为空
        //Assertions.assertNotNull(user.getId());
    }

    // 测试根据ID查询用户
    @Test
    public void testSelectById() {
        // 先新增一个用户
        User insertUser = new User();
        insertUser.setUsername("查询测试");
        insertUser.setAge(30);
        userMapper.insert(insertUser);

        // 查询用户
        //User selectUser = userMapper.selectById(insertUser.getId());
        // 断言：查询结果不为空
        //Assertions.assertNotNull(selectUser);
        // 断言：用户名一致
        //Assertions.assertEquals("查询测试", selectUser.getUsername());
    }
}
