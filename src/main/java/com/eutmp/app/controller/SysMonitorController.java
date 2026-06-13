package com.eutmp.app.controller;

import com.eutmp.app.service.SysMonitorService;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统监控控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/monitor")
public class SysMonitorController {

    @Autowired
    private SysMonitorService sysMonitorService;

    /**
     * 获取系统监控信息
     */
    @GetMapping("/system")
    public Result<Map<String, Object>> getSystemInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put("system", sysMonitorService.getSystemInfo());
        result.put("cpu", sysMonitorService.getCpuInfo());
        result.put("memory", sysMonitorService.getMemoryInfo());
        result.put("jvm", sysMonitorService.getJvmInfo());
        result.put("disk", sysMonitorService.getDiskInfo());

        return Result.success(result);
    }

    /**
     * 获取CPU信息
     */
    @GetMapping("/cpu")
    public Result<Map<String, Object>> getCpuInfo() {
        return Result.success(sysMonitorService.getCpuInfo());
    }

    /**
     * 获取内存信息
     */
    @GetMapping("/memory")
    public Result<Map<String, Object>> getMemoryInfo() {
        return Result.success(sysMonitorService.getMemoryInfo());
    }

    /**
     * 获取JVM信息
     */
    @GetMapping("/jvm")
    public Result<Map<String, Object>> getJvmInfo() {
        return Result.success(sysMonitorService.getJvmInfo());
    }

    /**
     * 获取磁盘信息
     */
    @GetMapping("/disk")
    public Result<Map<String, Object>> getDiskInfo() {
        return Result.success(sysMonitorService.getDiskInfo());
    }
}
