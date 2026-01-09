package com.learn.springbootcrud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.learn.springbootcrud.mapper")
@ServletComponentScan // 扫描@WebFilter/@WebFilter等注解
@SpringBootApplication
@EnableCaching // 启用SpringCache缓存
public class SpringbootcrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootcrudApplication.class, args);
	}

}
