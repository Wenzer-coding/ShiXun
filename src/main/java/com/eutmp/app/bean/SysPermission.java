package com.eutmp.app.bean;

import lombok.Data;

/**
 * 权限资源表实体类
 */
@Data
public class SysPermission {
    /**
     * 权限ID
     */
    private Long id;
    
    /**
     * 父权限ID(0顶级菜单)
     */
    private Long parentId;
    
    /**
     * 权限名称
     */
    private String permName;
    
    /**
     * 权限标识符(sys:user:list)
     */
    private String permKey;
    
    /**
     * 1菜单 2按钮 3接口
     */
    private Integer permType;
    
    /**
     * 前端路由地址
     */
    private String path;
    
    /**
     * 菜单图标
     */
    private String icon;
    
    /**
     * 排序号
     */
    private Integer sort;
    
    /**
     * 状态 0禁用 1启用
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
