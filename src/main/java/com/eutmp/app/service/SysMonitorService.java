package com.eutmp.app.service;

import java.util.Map;

/**
 * 系统监控Service接口
 */
public interface SysMonitorService {
    
    /**
     * 获取系统信息
     */
    Map<String, Object> getSystemInfo();
    
    /**
     * 获取CPU信息
     */
    Map<String, Object> getCpuInfo();
    
    /**
     * 获取内存信息
     */
    Map<String, Object> getMemoryInfo();
    
    /**
     * 获取JVM信息
     */
    Map<String, Object> getJvmInfo();
    
    /**
     * 获取磁盘信息
     */
    Map<String, Object> getDiskInfo();
}
