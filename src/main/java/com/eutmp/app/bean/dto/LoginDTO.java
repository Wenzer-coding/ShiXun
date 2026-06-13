package com.eutmp.app.bean.dto;

import lombok.Data;

/**
 * 登录DTO
 */
@Data
public class LoginDTO {
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
}
