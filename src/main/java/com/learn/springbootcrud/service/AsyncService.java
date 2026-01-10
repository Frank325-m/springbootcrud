package com.learn.springbootcrud.service;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsyncService {
    // @Async 注解：告诉Spring这是一个异步方法
    // 它会在一个单独的线程中运行，不会阻塞主线程
    @Async
    public void sendEmail(String email) {
        log.info("[Async Task] Send Message to: " + email);

        try {
            // 模拟耗时操作（比如连接邮件服务器，发送数据）
            TimeUnit.SECONDS.sleep(2); // 暂停2秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("[Async Task] Email Send OK");
    }
}
