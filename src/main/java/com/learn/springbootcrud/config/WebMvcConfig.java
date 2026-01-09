package com.learn.springbootcrud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.learn.springbootcrud.interceptorr.LogInterceptor;

import jakarta.annotation.Resource;

/**
 * WebMvc配置类: 注册拦截器,配置静态资源等
 */
@Configuration // 标识为配置类,替代xml配置
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private LogInterceptor logInterceptor;

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
            .addPathPatterns("/**") // 拦截所有路径
            // 排除接口文档相关路径
            .excludePathPatterns(
                "/doc.html", 
                "/swagger-resources/**",
                "/webjars/**", 
                "/v3/api-docs/**"
            );
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 放行Knife4j的静态资源
        registry.addResourceHandler("/doc.html")
            .addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
