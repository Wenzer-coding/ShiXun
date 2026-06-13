package com.eutmp.app.service;

import com.eutmp.app.bean.SysPermission;
import java.util.List;

/**
 * 权限资源表Service接口
 */
public interface SysPermissionService {
    
    /**
     * 根据ID查询权限
     */
    SysPermission selectById(Long id);
    
    /**
     * 查询所有权限
     */
    List<SysPermission> selectAll();
    
    /**
     * 根据父权限ID查询
     */
    List<SysPermission> selectByParentId(Long parentId);
    
    /**
     * 根据权限标识查询
     */
    SysPermission selectByPermKey(String permKey);
    
    /**
     * 新增权限
     */
    int insert(SysPermission sysPermission);
    
    /**
     * 更新权限
     */
    int update(SysPermission sysPermission);
    
    /**
     * 删除权限
     */
    int deleteById(Long id);
    
    /**
     * 根据用户ID查询权限菜单树
     */
    List<SysPermission> selectMenuTreeByUserId(Long userId);
    
    /**
     * 根据用户ID查询所有权限（包括按钮）
     */
    List<SysPermission> selectByUserId(Long userId);
}
