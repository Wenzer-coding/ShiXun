package com.eutmp.app.bean;

import lombok.Data;

/**
 * 系统操作日志表实体类
 */
@Data
public class SysOperLog {
    /**
     * 日志主键
     */
    private Long id;
    
    /**
     * 操作人ID
     */
    private Long userId;
    
    /**
     * 操作账号
     */
    private String username;
    
    /**
     * 操作人昵称
     */
    private String nickName;
    
    /**
     * 操作人所属部门
     */
    private Long deptId;
    
    /**
     * 操作模块：用户管理/角色管理
     */
    private String operModule;
    
    /**
     * 1新增 2修改 3删除 4查询 5导入 6导出
     */
    private Integer operType;
    
    /**
     * 操作描述
     */
    private String operDesc;
    
    /**
     * 请求地址
     */
    private String requestUrl;
    
    /**
     * 请求方式GET/POST
     */
    private String requestMethod;
    
    /**
     * 操作IP地址
     */
    private String operIp;
    
    /**
     * 请求参数
     */
    private String operParams;
    
    /**
     * 返回结果
     */
    private String jsonResult;
    
    /**
     * 耗时ms
     */
    private Long costTime;
    
    /**
     * 1成功 0失败
     */
    private Integer status;
    
    /**
     * 异常信息
     */
    private String errorMsg;
    
    /**
     * 创建时间
     */
    private String createTime;
}
