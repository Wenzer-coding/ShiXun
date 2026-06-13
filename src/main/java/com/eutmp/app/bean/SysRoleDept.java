package com.eutmp.app.bean;

import lombok.Data;

/**
 * 角色-数据权限(部门关联表)实体类
 */
@Data
public class SysRoleDept {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 可访问部门ID
     */
    private Long deptId;
}
