package com.eutmp.app.bean.dto;

import lombok.Data;

/**
 * 重置密码DTO
 */
@Data
public class ResetPasswordDTO {
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 验证码
     */
    private String verifyCode;
    
    /**
     * 新密码
     */
    private String newPassword;
}
