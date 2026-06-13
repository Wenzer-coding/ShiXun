package com.eutmp.app.bean.dto;

import com.eutmp.app.bean.SysPermission;
import lombok.Data;

import java.util.List;

/**
 * 用户信息DTO（包含菜单权限）
 */
@Data
public class UserInfoDTO {
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 登录账号
     */
    private String username;
    
    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 所属部门ID
     */
    private Long deptId;
    
    /**
     * 菜单权限树
     */
    private List<SysPermission> menus;
    
    /**
     * 按钮权限标识列表（用于前端按钮权限控制）
     */
    private List<String> permissions;
}
