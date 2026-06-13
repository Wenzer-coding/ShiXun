package com.eutmp.app.service.impl;

import com.eutmp.app.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.eutmp.app.utils.DateUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件服务实现类
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Override
    public boolean sendVerifyCode(String toEmail, String verifyCode) {
        try {
            log.info("开始发送验证码邮件 - 收件人: {}", toEmail);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("高校实训管理平台 - 密码重置验证码");
            
            // 构建邮件内容
            String content = buildEmailContent(verifyCode);
            message.setText(content);
            
            // 发送邮件
            mailSender.send(message);
            
            log.info("验证码邮件发送成功 - 收件人: {}", toEmail);
            return true;
            
        } catch (org.springframework.mail.MailAuthenticationException e) {
            log.error("邮件认证失败 - 收件人: {}, 错误: {}。请检查邮箱授权码是否有效。", toEmail, e.getMessage());
            return false;
        } catch (org.springframework.mail.MailSendException e) {
            log.error("邮件发送失败 - 收件人: {}, 错误: {}。可能是网络连接问题或SMTP服务器不可达。", toEmail, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("验证码邮件发送失败 - 收件人: {}, 错误类型: {}, 错误: {}", 
                     toEmail, e.getClass().getName(), e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 构建邮件内容
     */
    private String buildEmailContent(String verifyCode) {
        return "尊敬的用户，您好！\n\n" +
               "您正在使用高校实训管理平台的密码重置功能。\n\n" +
               "您的验证码是：" + verifyCode + "\n\n" +
               "验证码有效期为5分钟，请尽快完成密码重置。\n\n" +
               "如果这不是您本人的操作，请忽略此邮件。\n\n" +
               "高校实训管理平台\n" +
               DateUtils.now();
    }
}
