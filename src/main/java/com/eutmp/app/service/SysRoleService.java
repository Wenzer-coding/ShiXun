package com.eutmp.app.service;

import com.eutmp.app.bean.SysRole;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 角色表Service接口
 */
public interface SysRoleService {
    
    /**
     * 根据ID查询角色
     */
    SysRole selectById(Long id);
    
    /**
     * 查询所有角色
     */
    List<SysRole> selectAll();
    
    /**
     * 分页查询角色
     */
    PageResult<SysRole> selectByPage(int pageNum, int pageSize, String roleName, Integer status);
    
    /**
     * 根据角色编码查询
     */
    SysRole selectByRoleCode(String roleCode);
    
    /**
     * 新增角色
     */
    int insert(SysRole sysRole);
    
    /**
     * 更新角色
     */
    int update(SysRole sysRole);
    
    /**
     * 删除角色
     */
    int deleteById(Long id);
    
    /**
     * 检查角色是否被用户使用
     */
    boolean isRoleUsedByUsers(Long roleId);
    
    /**
     * 批量删除角色
     */
    int batchDeleteByIds(List<Long> ids);
}
