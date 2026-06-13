package com.eutmp.app.excepiton;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.SocketException;

/**
 * 全局异常处理器
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理404异常
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public String handle404Exception(HttpServletRequest request, Exception e) {
        log.error("404错误 - 请求路径: {}, 错误信息: {}", request.getRequestURI(), e.getMessage());
        return "error/404";
    }
    
    /**
     * 处理网络连接异常
     */
    @ExceptionHandler(SocketException.class)
    public String handleSocketException(HttpServletRequest request, SocketException e) {
        log.error("网络连接错误 - 请求路径: {}, 错误类型: Connection Reset, 错误信息: {}", 
                 request.getRequestURI(), e.getMessage(), e);
        // 记录详细的网络错误信息，帮助诊断问题
        log.error("可能的原因：1.邮件服务器连接失败 2.数据库连接中断 3.外部API调用失败");
        return "error/500";
    }
    
    /**
     * 处理500异常 - 所有其他未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public String handle500Exception(HttpServletRequest request, Exception e) {
        log.error("500错误 - 请求路径: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return "error/500";
    }
}
