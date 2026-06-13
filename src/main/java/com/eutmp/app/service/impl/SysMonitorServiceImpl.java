package com.eutmp.app.service.impl;

import com.eutmp.app.service.SysMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * 系统监控Service实现类
 */
@Slf4j
@Service
public class SysMonitorServiceImpl implements SysMonitorService {
    
    private final SystemInfo systemInfo = new SystemInfo();
    private final long startTime; // 项目启动时间
    
    public SysMonitorServiceImpl() {
        // 获取JVM启动时间（项目启动时间）
        this.startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        log.info("系统监控服务初始化，项目启动时间: {}", new Date(startTime));
    }
    
    @Override
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();
        
        // 操作系统信息
        OperatingSystem os = this.systemInfo.getOperatingSystem();
        systemInfo.put("osName", os.getFamily() + " " + os.getVersionInfo().getVersion());
        systemInfo.put("osArch", os.getBitness() + "位");
        
        // 计算机信息
        systemInfo.put("computerName", this.systemInfo.getHardware().getComputerSystem().getManufacturer() + " " + 
                      this.systemInfo.getHardware().getComputerSystem().getModel());
        
        // 系统运行时间
        systemInfo.put("systemUptime", formatTimeInterval(os.getSystemUptime()));
        
        // 项目启动时间
        systemInfo.put("projectStartTime", formatDateTime(new Date(startTime)));
        
        // 项目运行时间
        long uptime = System.currentTimeMillis() - startTime;
        systemInfo.put("projectUptime", formatInterval(uptime));
        
        return systemInfo;
    }
    
    @Override
    public Map<String, Object> getCpuInfo() {
        Map<String, Object> cpuInfo = new HashMap<>();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        
        // CPU基本信息
        cpuInfo.put("cpuName", processor.getProcessorIdentifier().getName());
        cpuInfo.put("cpuCores", processor.getLogicalProcessorCount() + "核");
        cpuInfo.put("cpuPhysicalCores", processor.getPhysicalProcessorCount() + "核");
        
        // CPU使用率
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            Thread.sleep(1000); // 等待1秒获取准确的CPU使用率
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        
        long totalTick = 0;
        long idleTick = 0;
        for (int i = 0; i < ticks.length; i++) {
            totalTick += ticks[i] - prevTicks[i];
            if (i == CentralProcessor.TickType.IDLE.getIndex()) {
                idleTick += ticks[i] - prevTicks[i];
            }
        }
        
        double cpuUsage = totalTick > 0 ? (1.0 - (double) idleTick / totalTick) * 100 : 0;
        cpuInfo.put("cpuUsage", String.format("%.2f%%", cpuUsage));
        
        // CPU负载
        double[] loadAverage = processor.getSystemLoadAverage(3);
        cpuInfo.put("cpuLoad1", loadAverage[0] > 0 ? String.format("%.2f", loadAverage[0]) : "N/A");
        cpuInfo.put("cpuLoad5", loadAverage[1] > 0 ? String.format("%.2f", loadAverage[1]) : "N/A");
        cpuInfo.put("cpuLoad15", loadAverage[2] > 0 ? String.format("%.2f", loadAverage[2]) : "N/A");
        
        return cpuInfo;
    }
    
    @Override
    public Map<String, Object> getMemoryInfo() {
        Map<String, Object> memoryInfo = new HashMap<>();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;
        
        memoryInfo.put("totalMemory", formatBytes(totalMemory));
        memoryInfo.put("usedMemory", formatBytes(usedMemory));
        memoryInfo.put("availableMemory", formatBytes(availableMemory));
        memoryInfo.put("memoryUsage", String.format("%.2f%%", (double) usedMemory / totalMemory * 100));
        
        return memoryInfo;
    }
    
    @Override
    public Map<String, Object> getJvmInfo() {
        Map<String, Object> jvmInfo = new HashMap<>();
        
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        jvmInfo.put("jvmName", System.getProperty("java.vm.name"));
        jvmInfo.put("jvmVersion", System.getProperty("java.version"));
        jvmInfo.put("jvmVendor", System.getProperty("java.vendor"));
        jvmInfo.put("totalMemory", formatBytes(totalMemory));
        jvmInfo.put("usedMemory", formatBytes(usedMemory));
        jvmInfo.put("freeMemory", formatBytes(freeMemory));
        jvmInfo.put("maxMemory", formatBytes(maxMemory));
        jvmInfo.put("memoryUsage", String.format("%.2f%%", (double) usedMemory / maxMemory * 100));
        
        return jvmInfo;
    }
    
    @Override
    public Map<String, Object> getDiskInfo() {
        Map<String, Object> diskInfo = new HashMap<>();
        List<Map<String, Object>> diskList = new ArrayList<>();
        
        FileSystem fileSystem = systemInfo.getOperatingSystem().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        
        for (OSFileStore store : fileStores) {
            Map<String, Object> disk = new HashMap<>();
            disk.put("name", store.getName());
            disk.put("type", store.getType());
            disk.put("mount", store.getMount());
            
            long totalSpace = store.getTotalSpace();
            long usableSpace = store.getUsableSpace();
            long usedSpace = totalSpace - usableSpace;
            
            disk.put("totalSpace", formatBytes(totalSpace));
            disk.put("usedSpace", formatBytes(usedSpace));
            disk.put("freeSpace", formatBytes(usableSpace));
            disk.put("usage", String.format("%.2f%%", (double) usedSpace / totalSpace * 100));
            
            diskList.add(disk);
        }
        
        diskInfo.put("disks", diskList);
        return diskInfo;
    }
    
    /**
     * 格式化字节
     */
    private String formatBytes(long bytes) {
        if (bytes == 0) {
            return "0 B";
        }
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double value = bytes;
        
        while (value >= 1024 && unitIndex < units.length - 1) {
            value /= 1024;
            unitIndex++;
        }
        
        return String.format("%.2f %s", value, units[unitIndex]);
    }
    
    /**
     * 格式化时间间隔（秒）
     */
    private String formatTimeInterval(long seconds) {
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        
        if (days > 0) {
            return String.format("%d天%d小时%d分钟", days, hours, minutes);
        } else if (hours > 0) {
            return String.format("%d小时%d分钟", hours, minutes);
        } else {
            return String.format("%d分钟", minutes);
        }
    }
    
    /**
     * 格式化时间间隔（毫秒）
     */
    private String formatInterval(long milliseconds) {
        long seconds = milliseconds / 1000;
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        if (days > 0) {
            return String.format("%d天%d小时%d分钟%d秒", days, hours, minutes, secs);
        } else if (hours > 0) {
            return String.format("%d小时%d分钟%d秒", hours, minutes, secs);
        } else if (minutes > 0) {
            return String.format("%d分钟%d秒", minutes, secs);
        } else {
            return String.format("%d秒", secs);
        }
    }
    
    /**
     * 格式化日期时间
     */
    private String formatDateTime(Date date) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
