package com.learn.springbootcrud.filter;

import java.io.IOException;

import org.springframework.util.StringUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * 接口鉴权过滤器
 * 验证请求投中的Token,无Token/Token错误则拒绝访问
 */
@Slf4j
@WebFilter(urlPatterns = {"/user/**"}) // 拦截所有/user开头的接口
public class AuthFilter implements Filter {

    // 模拟合法Token(实际项目中从Redis/数据库获取)
    private static final String VALID_TOKEN = "learn_java_2026";

    // 过滤器初始化（可选）
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("===== AuthFilter Init Success =====");
    }

    // 过滤器核心方法,处理请求
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 转换为HTTP请求/响应对象
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        
        String requestURI = request.getRequestURI();
        log.info("===== AuthFilter Interceptor: {} =====", requestURI);

        // 获取请求头中的Token
        String token = request.getHeader("Authoriztion");
        log.info("recived Token: {}", token);

        // 校验Token
        if (!StringUtils.hasText(token) || !VALID_TOKEN.equals(token)) {
            // Token无效:返回401未授权,终止请求
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("\"code\":401,\"msg\":\"Token Invailed\",\"data\":null");
            return;
        }

        // Token有效:放行,继续执行后续过滤器/拦截器/Controller
        log.info("Token is OK, You can go: {}", requestURI);
        filterChain.doFilter(request, response);
    }
    
    // 过滤器销毁（可选）
    @Override
    public void destroy() {
        log.info("===== AuthFilter destroy =====");
    }
}
