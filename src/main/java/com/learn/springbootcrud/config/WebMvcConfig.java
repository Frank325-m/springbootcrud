package com.learn.springbootcrud.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.learn.springbootcrud.filter.AuthFilter;
import com.learn.springbootcrud.interceptorr.LogInterceptor;

import jakarta.servlet.Filter;

/**
 * WebMvc配置类: 注册拦截器,配置静态资源等
 */
@Configuration // 标识为配置类,替代xml配置
public class WebMvcConfig implements WebMvcConfigurer {

    // 注册过滤器
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.addServletNames("dispatcherServlet");
        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns("/user/**", "/task/**");
        //registrationBean.setUrlPatterns(Arrays.asList("/user/**", "/task/**"));
        registrationBean.setOrder(1);
        registrationBean.setEnabled(true);
        return registrationBean;
    }


    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
            .addPathPatterns("/**") // 拦截所有路径
            // 排除接口文档相关路径
            .excludePathPatterns(
                "/doc.html", 
                "/swagger-resources/**",
                "/webjars/**", 
                "/v3/api-docs/**"
            )
            .order(1);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 放行Knife4j的静态资源
        registry.addResourceHandler("/doc.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
