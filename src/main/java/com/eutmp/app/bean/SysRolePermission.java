package com.eutmp.app.bean;

import lombok.Data;

/**
 * 角色权限关联表实体类
 */
@Data
public class SysRolePermission {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 权限ID
     */
    private Long permId;
}
