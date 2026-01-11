package com.learn.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springbootcrud.common.Result;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/redis")
    public Result<String> testRedis() {
        redisTemplate.opsForValue().set("test_key", "hello_redis_success");

        String value = (String)redisTemplate.opsForValue().get("test_key");

        return Result.success("Redis Connect is Success, Read message is: "+value);
    }

    @GetMapping("/config")
    public Result<String> testRedisConfig(
            @Value("${spring.redis.host}") String redisHost,
            @Value("${spring.redis.port}") Integer redisPort,
            @Value("${spring.redis.password}") Integer redisPassword) {
        String msg = String.format("Redis Config; host=%s, port=%s, password=%s", redisHost, redisPort, redisPassword);
        log.info(msg);

        return Result.success(msg);
    }
}
