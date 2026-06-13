package com.eutmp.app.bean;

import lombok.Data;

/**
 * 用户角色关联表实体类
 */
@Data
public class SysUserRole {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 用户id
     */
    private Long userId;
    
    /**
     * 角色id
     */
    private Long roleId;
}
