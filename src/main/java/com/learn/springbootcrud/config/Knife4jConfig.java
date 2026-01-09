package com.learn.springbootcrud.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Knife4J 接口文档配置
 */
// 启用Knife4j 增强功能
@EnableKnife4j
// 核心：启用OpenAPI3.0（替代Swagger 2的 @EnableSwagger2）
@Configuration
public class Knife4jConfig {

    // 配置文件基本信息
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户管理接口")
                .pathsToMatch("/user/**")
                .build();
    }

    // 配置文件基本信息
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpringBoot CRUD 文档接口")
                        .version("1.0")
                        .description("基于SpringBoot+MyBatis-Plus的用户管理接口文档")
                        .contact(new Contact()
                                .name("Frank")
                                .email("Frank@gmail.com")));           
    }
}