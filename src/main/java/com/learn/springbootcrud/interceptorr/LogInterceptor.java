package com.learn.springbootcrud.interceptorr;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Lombok日志注解,替代System.out.println
@Component // 交给Spring管理
public class LogInterceptor implements HandlerInterceptor {
    
    // 前置拦截:Controller方法执行前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 只拦截 Controller的方法(排除静态资源等)
        if (handler instanceof HandlerMethod) {
            // 记录请求开始时间(出入request属性)
            long startTime = System.currentTimeMillis();
            request.setAttribute("startTime", startTime);

            // 打印请求基本信息
            String url = request.getRequestURI().toString();
            String method = request.getMethod();
            String ip = request.getRemoteAddr();
            String className = ((HandlerMethod)handler).getBeanType().getName();
            String methodName = ((HandlerMethod)handler).getMethod().getName();

            log.info("===== Request Start =====");
            log.info("Request URL: {}", url);
            log.info("Request Method: {}", method);
            log.info("Client IP: {}", ip);
            log.info("Request Class Name: {}", className);
            log.info("Request Method Name: {}", methodName);
        }
        return true;
    }

    // 后置拦截:Controller方法执行后,视图渲染前调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 此处可处理响应数据
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 计算请求耗时
            long startTime = (long)request.getAttribute("startTime");
            long endTime = System.currentTimeMillis();
            long costTime = endTime - startTime;

            // 打印响应信息
            int status = response.getStatus();
            log.info("Response Status: {}", status);
            log.info("Request cost Time: {}ms", costTime);
            log.info("===== Request Over =====\n");
        }
    }
}
