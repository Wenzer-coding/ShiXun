package com.eutmp.app.service;

/**
 * 邮件服务接口
 */
public interface EmailService {
    
    /**
     * 发送验证码邮件
     * 
     * @param toEmail 收件人邮箱
     * @param verifyCode 验证码
     * @return 是否发送成功
     */
    boolean sendVerifyCode(String toEmail, String verifyCode);
}
