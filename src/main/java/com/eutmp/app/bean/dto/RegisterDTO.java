package com.eutmp.app.bean.dto;

import lombok.Data;

/**
 * 注册DTO
 */
@Data
public class RegisterDTO {
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 昵称
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
}
