package com.eutmp.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制器
 */
@Slf4j
@Controller
@RequestMapping
public class PageController {

    /**
     * 首页 - 重定向到登录页面
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
    
    /**
     * 登录后的首页
     */
    @GetMapping("/index")
    public String home() {
        return "index";
    }
    
    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    /**
     * 找回密码页面
     */
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
    
    /**
     * 404错误页面
     */
    @GetMapping("/error/404")
    public String error404() {
        return "error/404";
    }
    
    /**
     * 500错误页面
     */
    @GetMapping("/error/500")
    public String error500() {
        return "error/500";
    }
    
    /**
     * 部门管理页面
     */
    @GetMapping("/system/dept")
    public String deptManage() {
        return "system/dept";
    }
    
    /**
     * 操作日志页面
     */
    @GetMapping("/system/log")
    public String operLog() {
        return "system/operlog";
    }
    
    /**
     * SQL监控页面（Druid）
     */
    @GetMapping("/system/druid")
    public String druidMonitor() {
        log.info("访问SQL监控页面");
        return "system/druid-monitor";
    }
    
    /**
     * 用户管理页面
     */
    @GetMapping("/system/user")
    public String userManage() {
        return "system/user";
    }
    
    /**
     * 角色管理页面
     */
    @GetMapping("/system/role")
    public String roleManage() {
        return "system/role";
    }
    
    /**
     * 系统监控页面
     */
    @GetMapping("/system/monitor")
    public String systemMonitor() {
        log.info("访问系统监控页面");
        return "system/monitor";
    }
    
    /**
     * 菜单权限管理页面
     */
    @GetMapping("/system/menu")
    public String menuManage() {
        log.info("访问菜单权限管理页面");
        return "system/menu";
    }
    
    // ==================== 数据管理页面 ====================
    
    /**
     * 学院管理页面
     */
    @GetMapping("/data/college")
    public String collegeManage() {
        return "data/college";
    }
    
    /**
     * 专业管理页面
     */
    @GetMapping("/data/major")
    public String majorManage() {
        return "data/major";
    }
    
    /**
     * 班级管理页面
     */
    @GetMapping("/data/class")
    public String classManage() {
        return "data/class";
    }
    
    /**
     * 课程管理页面
     */
    @GetMapping("/data/course")
    public String courseManage() {
        return "data/course";
    }
    
    /**
     * 学生管理页面
     */
    @GetMapping("/data/student")
    public String studentManage() {
        return "data/student";
    }
    
    /**
     * 成绩管理页面
     */
    @GetMapping("/data/score")
    public String scoreManage() {
        return "data/score";
    }
    
    /**
     * 实训项目管理页面
     */
    @GetMapping("/data/project")
    public String projectManage() {
        return "data/project";
    }
    
    /**
     * 实训开班管理页面
     */
    @GetMapping("/data/plan")
    public String planManage() {
        return "data/plan";
    }
    
    /**
     * 报名管理页面
     */
    @GetMapping("/data/enroll")
    public String enrollManage() {
        return "data/enroll";
    }
    
    /**
     * 考勤管理页面
     */
    @GetMapping("/data/attendance")
    public String attendanceManage() {
        return "data/attendance";
    }
    
    /**
     * 耗材管理页面
     */
    @GetMapping("/data/material")
    public String materialManage() {
        return "data/material";
    }
    
    /**
     * 耗材领用管理页面
     */
    @GetMapping("/data/materialUse")
    public String materialUseManage() {
        return "data/materialUse";
    }
    
    /**
     * 实训成绩管理页面
     */
    @GetMapping("/data/trainScore")
    public String trainScoreManage() {
        return "data/trainScore";
    }

    /**
     * 学生签到页面（通过二维码扫码访问）
     */
    @GetMapping("/attendance/signin")
    public String studentSignIn() {
        return "data/attendance-signin";
    }
}
