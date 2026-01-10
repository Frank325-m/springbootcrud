package com.learn.springbootcrud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springbootcrud.common.Result;
import com.learn.springbootcrud.service.AsyncService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "任务管理", description = "异步与定时任务测试")
@RestController
@RequestMapping("/task")
public class TaskController {
    
    @Resource
    private AsyncService asyncService;

    @Operation(summary="测试异步任务", description="注册用户并发送邮件")
    @GetMapping("/smsg")
    public Result<String> testAsync() {
        log.info("Controller Work .... ");

        // 调用异步方法
        // 注意，这里不会等待2秒，而是立即返回
        asyncService.sendEmail("user@example.com");
        
        log.info("Controller done, back to front.");
        
        return Result.success("Registry apply was recived, the email is sending in the backend");
    }
}
