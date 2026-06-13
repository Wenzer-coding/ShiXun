package com.eutmp.app.bean;

import lombok.Data;

/**
 * 角色表实体类
 */
@Data
public class SysRole {
    /**
     * 角色ID
     */
    private Long id;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 角色标识(admin/user)
     */
    private String roleCode;
    
    /**
     * 角色备注
     */
    private String remark;
    
    /**
     * 0禁用1启用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 更新时间
     */
    private String updateTime;
}
