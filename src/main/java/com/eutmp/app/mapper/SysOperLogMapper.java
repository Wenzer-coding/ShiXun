package com.eutmp.app.mapper;

import com.eutmp.app.bean.SysOperLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统操作日志表Mapper接口
 */
@Mapper
public interface SysOperLogMapper {
    
    /**
     * 根据ID查询日志
     */
    SysOperLog selectById(Long id);
    
    /**
     * 查询所有日志
     */
    List<SysOperLog> selectAll();
    
    /**
     * 条件查询日志（用于分页）
     */
    List<SysOperLog> selectByCondition(@Param("operModule") String operModule, 
                                       @Param("username") String username, 
                                       @Param("createTime") String createTime);
    
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
    int deleteBatch(@Param("ids") Long[] ids);
}
