package com.learn.springbootcrud.config;


import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    
    @Bean(name = "myAsyncPool")  // 给线程池起个名字
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 核心线程数：线程池创建时初始化的线程数
        executor.setCorePoolSize(5);
        // 最大线程数：线程池最大的线程数，只有缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20);
        // 队列容量：用来缓冲执行任务的队列
        executor.setQueueCapacity(100);
        // 线程名称前缀，方便排除问题
        executor.setThreadNamePrefix("MyAsyncThread-");
        // 拒绝策略：当队列满了且最大线程数也满了，如何处理任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行（即主线程自己跑）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();

        return executor;
    }
}
