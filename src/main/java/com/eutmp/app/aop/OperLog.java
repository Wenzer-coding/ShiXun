package com.eutmp.app.aop;

import java.lang.annotation.*;

/**
 * 操作日志自定义注解
 * 用于标记需要记录操作日志的方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    
    /**
     * 操作模块名称
     * 例如：用户管理、角色管理、部门管理等
     */
    String module() default "";
    
    /**
     * 操作类型
     * 1新增 2修改 3删除 4查询 5导入 6导出
     */
    int operType() default 0;
    
    /**
     * 操作描述
     * 例如：新增用户、修改角色、删除部门等
     */
    String description() default "";
}
