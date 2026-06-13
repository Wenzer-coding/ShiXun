package com.eutmp.app.service;

import com.eutmp.app.bean.SysOperLog;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 系统操作日志表Service接口
 */
public interface SysOperLogService {
    
    /**
     * 根据ID查询日志
     */
    SysOperLog selectById(Long id);
    
    /**
     * 查询所有日志
     */
    List<SysOperLog> selectAll();
    
    /**
     * 分页查询日志
     */
    PageResult<SysOperLog> selectByPage(Integer pageNum, Integer pageSize, String operModule, String username, String createTime);
    
    /**
     * 根据用户ID查询日志
     */
    List<SysOperLog> selectByUserId(Long userId);
    
    /**
     * 新增日志
     */
    int insert(SysOperLog sysOperLog);
    
    /**
     * 更新日志
     */
    int update(SysOperLog sysOperLog);
    
    /**
     * 删除日志
     */
    int deleteById(Long id);
    
    /**
     * 批量删除日志
     */
    int deleteBatch(Long[] ids);
}
