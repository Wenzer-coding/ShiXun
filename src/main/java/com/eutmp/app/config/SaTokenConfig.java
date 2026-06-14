package com.eutmp.app.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Sa-Token 配置类
 */
@Slf4j
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    
    /**
     * 注册 Sa-Token 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册登录验证拦截器
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String requestURI = request.getRequestURI();
                
                log.debug("Sa-Token拦截器 - 请求URI: {}", requestURI);
                
                // 排除不需要登录的路径
                if (requestURI.equals("/login") ||
                    requestURI.equals("/register") ||
                    requestURI.equals("/forgot-password") ||
                    requestURI.startsWith("/api/auth/") ||
                    requestURI.equals("/api/attendance/signin") ||
                    requestURI.startsWith("/api/ai/") ||
                    requestURI.startsWith("/druid/") ||
                    requestURI.equals("/druid") ||
                    requestURI.equals("/attendance/signin") ||
                    requestURI.startsWith("/css/") ||
                    requestURI.startsWith("/js/") ||
                    requestURI.startsWith("/images/") ||
                    requestURI.startsWith("/fonts/") ||
                    requestURI.equals("/favicon.ico") ||
                    requestURI.startsWith("/error")) {
                    log.debug("Sa-Token拦截器 - 放行路径: {}", requestURI);
                    return true;
                }
                
                // 检查是否登录
                try {
                    StpUtil.checkLogin();
                    return true;
                } catch (NotLoginException e) {
                    // 获取未登录的原因
                    String type = e.getType();
                    log.warn("登录验证失败 - URI: {}, 类型: {}", requestURI, type);
                    
                    // 根据不同的未登录类型进行处理
                    if (NotLoginException.KICK_OUT.equals(type)) {
                        // 账号被踢下线，重定向到登录页并提示
                        log.info("账号在其他设备登录，已被踢下线");
                        StpUtil.logout();
                        response.sendRedirect("/login?message=kicked_out");
                    } else if (NotLoginException.TOKEN_TIMEOUT.equals(type)) {
                        // Token过期
                        log.info("Token已过期");
                        StpUtil.logout();
                        response.sendRedirect("/login");
                    } else if (NotLoginException.BE_REPLACED.equals(type)) {
                        // Token被顶替
                        log.info("Token被顶替");
                        StpUtil.logout();
                        response.sendRedirect("/login?message=kicked_out");
                    } else {
                        // 其他未登录情况
                        StpUtil.logout();
                        response.sendRedirect("/login");
                    }
                    return false;
                } catch (Exception e) {
                    // 其他异常，重定向到登录页面
                    log.warn("未登录或Token无效，重定向到登录页面: {}", requestURI);
                    StpUtil.logout();
                    response.sendRedirect("/login");
                    return false;
                }
            }
        }).addPathPatterns("/**");
    }
}
