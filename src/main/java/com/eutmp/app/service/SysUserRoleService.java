package com.eutmp.app.service;

import com.eutmp.app.bean.SysUserRole;
import java.util.List;

/**
 * 用户角色关联表Service接口
 */
public interface SysUserRoleService {
    
    /**
     * 根据ID查询
     */
    SysUserRole selectById(Long id);
    
    /**
     * 根据用户ID查询
     */
    List<SysUserRole> selectByUserId(Long userId);
    
    /**
     * 根据角色ID查询
     */
    List<SysUserRole> selectByRoleId(Long roleId);
    
    /**
     * 新增
     */
    int insert(SysUserRole sysUserRole);
    
    /**
     * 批量新增
     */
    int batchInsert(List<SysUserRole> list);
    
    /**
     * 根据用户ID删除
     */
    int deleteByUserId(Long userId);
    
    /**
     * 根据用户ID和角色ID列表批量删除
     */
    int batchDeleteByUserIdAndRoleIds(Long userId, List<Long> roleIds);
    
    /**
     * 删除
     */
    int deleteById(Long id);
}
