package com.eutmp.app.aop;

import cn.dev33.satoken.stp.StpUtil;
import com.eutmp.app.bean.SysOperLog;
import com.eutmp.app.bean.SysUser;
import com.eutmp.app.service.SysUserService;
import com.eutmp.app.service.SysOperLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.eutmp.app.utils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志AOP切面类
 * 用于自动记录系统的操作日志
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    
    @Autowired
    private SysOperLogService sysOperLogService;
    
    @Autowired
    private SysUserService sysUserService;
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    // 使用ThreadLocal存储开始时间
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();
    
    /**
     * 定义切点：扫描所有使用@OperLog注解的方法
     */
    @Pointcut("@annotation(com.eutmp.app.aop.OperLog)")
    public void operLogPointcut() {
    }
    
    /**
     * 前置通知：在方法执行前记录开始时间
     */
    @Before("operLogPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 记录开始时间（毫秒）
        START_TIME.set(System.currentTimeMillis());
        log.debug("操作日志切面开始执行");
    }
    
    /**
     * 后置通知：在方法执行后记录操作日志
     */
    @AfterReturning(pointcut = "operLogPointcut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        saveOperLog(joinPoint, result, null);
    }
    
    /**
     * 异常通知：方法抛出异常时也记录日志
     */
    @AfterThrowing(pointcut = "operLogPointcut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        saveOperLog(joinPoint, null, exception);
    }
    
    /**
     * 保存操作日志
     */
    private void saveOperLog(JoinPoint joinPoint, Object result, Exception exception) {
        try {
            // 创建日志对象
            SysOperLog operLog = new SysOperLog();
            
            // 获取注解信息
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            OperLog operLogAnnotation = method.getAnnotation(OperLog.class);
            
            if (operLogAnnotation != null) {
                operLog.setOperModule(operLogAnnotation.module());
                operLog.setOperType(operLogAnnotation.operType());
                operLog.setOperDesc(operLogAnnotation.description());
            }
            
            // 获取请求信息
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                operLog.setRequestUrl(request.getRequestURI());
                operLog.setRequestMethod(request.getMethod());
                operLog.setOperIp(getIpAddr(request));
                
                // 获取请求参数
                try {
                    Map<String, String[]> parameterMap = request.getParameterMap();
                    if (!parameterMap.isEmpty()) {
                        operLog.setOperParams(objectMapper.writeValueAsString(parameterMap));
                    }
                } catch (Exception e) {
                    log.warn("获取请求参数失败", e);
                }
            }
            
            // 获取当前登录用户信息
            try {
                if (StpUtil.isLogin()) {
                    Long userId = StpUtil.getLoginIdAsLong();
                    operLog.setUserId(userId);
                    
                    // 查询用户详细信息
                    SysUser user = sysUserService.selectById(userId);
                    if (user != null) {
                        operLog.setUsername(user.getUsername());
                        operLog.setNickName(user.getNickName());
                        operLog.setDeptId(user.getDeptId());
                    }
                }
            } catch (Exception e) {
                log.warn("获取用户信息失败", e);
            }
            
            // 设置返回结果
            if (result != null) {
                try {
                    operLog.setJsonResult(objectMapper.writeValueAsString(result));
                } catch (Exception e) {
                    log.warn("序列化返回结果失败", e);
                }
            }
            
            // 设置执行状态
            if (exception != null) {
                operLog.setStatus(0); // 失败
                operLog.setErrorMsg(exception.getMessage());
            } else {
                operLog.setStatus(1); // 成功
            }
            
            // 设置创建时间
            operLog.setCreateTime(DateUtils.now());
            
            // 计算耗时（毫秒）
            Long startTime = START_TIME.get();
            if (startTime != null) {
                long costTime = System.currentTimeMillis() - startTime;
                operLog.setCostTime(costTime);
                // 清除ThreadLocal避免内存泄漏
                START_TIME.remove();
            }
            
            // 保存日志
            sysOperLogService.insert(operLog);
            
            log.info("操作日志记录成功: {} - {}", operLog.getOperModule(), operLog.getOperDesc());
            
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }
    
    /**
     * 获取真实IP地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}

