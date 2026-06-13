package com.eutmp.app.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 系统用户表实体类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUser {
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 登录账号
     */
    private String username;
    
    /**
     * 加密密码(BCrypt)
     */
    private String password;
    
    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 所属部门ID
     */
    private Long deptId;
    
    /**
     * 状态 0禁用 1正常
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
    
    /**
     * 角色ID列表（用于接收前端传递的角色IDs）
     */
    private List<Long> roleIds;
    
    /**
     * 角色列表（用于返回用户拥有的角色）
     */
    private List<SysRole> roles;
}
