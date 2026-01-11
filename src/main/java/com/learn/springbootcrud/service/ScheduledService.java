package com.learn.springbootcrud.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduledService {
    // private static Integer count = 0;
    // @Scheduled 注解：配置执行规则
    // fixedRate = 2000,表示每隔2000毫秒（2秒）执行一次
    // 即使上一次任务还没跑完，时间到了也会开启新的（通常配合线程池）
    @Scheduled(fixedRate = 2000)
    public void reportCurrentTime() {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // count++;
        // if (count >= 30) {
        //     count = 0;
        //     log.info("[Timer Task] CurrentTime: " + time + "- doing clean datas...");
        // }
    }

    // 补充：cron表达式（更强大的定时规则）
    // @scheduled(cron = "0/5 * * * * ?") // 每5秒执行一次
}
