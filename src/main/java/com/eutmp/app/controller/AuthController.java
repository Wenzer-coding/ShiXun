package com.eutmp.app.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.eutmp.app.bean.SysPermission;
import com.eutmp.app.bean.SysUser;
import com.eutmp.app.bean.dto.LoginDTO;
import com.eutmp.app.bean.dto.RegisterDTO;
import com.eutmp.app.bean.dto.ResetPasswordDTO;
import com.eutmp.app.bean.dto.SendCodeDTO;
import com.eutmp.app.bean.dto.UserInfoDTO;
import com.eutmp.app.service.SysPermissionService;
import com.eutmp.app.service.SysUserService;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private SysUserService sysUserService;
    
    @Autowired
    private SysPermissionService sysPermissionService;
    
    @Autowired
    private com.eutmp.app.service.EmailService emailService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // 调用登录服务
            SysUser user = sysUserService.login(loginDTO.getUsername(), loginDTO.getPassword());
            
            if (user == null) {
                return Result.error("用户名或密码错误");
            }
            
            if (user.getStatus() == 0) {
                return Result.error("账号已被禁用，请联系管理员");
            }
            
            // Sa-Token 登录
            StpUtil.login(user.getId());
            
            log.info("用户登录成功: {}", user.getUsername());
            return Result.success("登录成功");
        } catch (Exception e) {
            log.error("登录失败", e);
            return Result.error("登录失败，请稍后重试");
        }
    }
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            // 参数验证
            if (registerDTO.getUsername() == null || registerDTO.getUsername().trim().isEmpty()) {
                return Result.error("用户名不能为空");
            }
            
            if (registerDTO.getPassword() == null || registerDTO.getPassword().length() < 6) {
                return Result.error("密码长度至少6个字符");
            }
            
            // 调用注册服务
            boolean success = sysUserService.register(registerDTO);
            
            if (success) {
                log.info("用户注册成功: {}", registerDTO.getUsername());
                return Result.success("注册成功");
            } else {
                return Result.error("用户名已存在");
            }
        } catch (Exception e) {
            log.error("注册失败", e);
            return Result.error("注册失败，请稍后重试");
        }
    }
    
    /**
     * 发送密码重置验证码
     */
    @PostMapping("/send-reset-code")
    public Result<?> sendResetCode(@RequestBody SendCodeDTO sendCodeDTO) {
        try {
            String email = sendCodeDTO.getEmail();
            
            if (email == null || email.trim().isEmpty()) {
                return Result.error("邮箱不能为空");
            }
            
            // 发送验证码
            boolean success = sysUserService.sendResetCode(email);
            
            if (success) {
                return Result.success("验证码已发送到您的邮箱");
            } else {
                return Result.error("该邮箱未注册");
            }
        } catch (Exception e) {
            log.error("发送验证码失败", e);
            return Result.error("发送验证码失败，请稍后重试");
        }
    }
    
    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    public Result<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            // 参数验证
            if (resetPasswordDTO.getNewPassword() == null || resetPasswordDTO.getNewPassword().length() < 6) {
                return Result.error("密码长度至少6个字符");
            }
            
            // 重置密码
            boolean success = sysUserService.resetPassword(
                resetPasswordDTO.getEmail(),
                resetPasswordDTO.getVerifyCode(),
                resetPasswordDTO.getNewPassword()
            );
            
            if (success) {
                return Result.success("密码重置成功");
            } else {
                return Result.error("验证码错误或已过期");
            }
        } catch (Exception e) {
            log.error("重置密码失败", e);
            return Result.error("重置密码失败，请稍后重试");
        }
    }
    
    /**
     * 检查登录状态
     */
    @GetMapping("/check-login")
    public Result<?> checkLogin() {
        try {
            // 检查是否已登录
            if (StpUtil.isLogin()) {
                return Result.success("已登录");
            } else {
                return Result.error("未登录");
            }
        } catch (Exception e) {
            log.error("检查登录状态失败", e);
            return Result.error("检查失败");
        }
    }
    
    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result<?> logout() {
        try {
            // 检查是否已登录
            if (StpUtil.isLogin()) {
                Long userId = StpUtil.getLoginIdAsLong();
                log.info("用户退出登录: userId={}", userId);
                // 执行退出
                StpUtil.logout();
            } else {
                log.warn("用户未登录，直接返回成功");
            }
            return Result.success("退出成功");
        } catch (Exception e) {
            log.error("退出登录失败", e);
            // 即使出错也返回成功，确保前端能跳转
            return Result.success("退出成功");
        }
    }
    
    /**
     * 获取当前用户信息（包含菜单权限）
     */
    @GetMapping("/userinfo")
    public Result<UserInfoDTO> getUserInfo() {
        try {
            // 检查是否已登录
            if (!StpUtil.isLogin()) {
                return Result.error("未登录");
            }
            
            // 获取用户ID
            Long userId = StpUtil.getLoginIdAsLong();
            log.info("=== 获取用户信息，用户ID: {} ===", userId);
            
            // 查询用户信息
            SysUser user = sysUserService.selectById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }
            
            // 查询用户菜单权限
            List<SysPermission> menus = sysPermissionService.selectMenuTreeByUserId(userId);
            log.info("用户 {} 的菜单数量: {}", userId, menus != null ? menus.size() : 0);
            log.info("用户 {} 的菜单列表: {}", userId, menus);
            
            // 查询用户所有权限（包括按钮）
            List<SysPermission> allPermissions = sysPermissionService.selectByUserId(userId);
            log.info("用户 {} 的所有权限数量: {}", userId, allPermissions != null ? allPermissions.size() : 0);
            
            List<String> permKeys = allPermissions.stream()
                    .filter(p -> p.getStatus() == 1)
                    .map(SysPermission::getPermKey)
                    .collect(java.util.stream.Collectors.toList());
            
            // 组装返回数据
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(user.getId());
            userInfoDTO.setUsername(user.getUsername());
            userInfoDTO.setNickName(user.getNickName());
            userInfoDTO.setEmail(user.getEmail());
            userInfoDTO.setPhone(user.getPhone());
            userInfoDTO.setDeptId(user.getDeptId());
            userInfoDTO.setMenus(menus);
            userInfoDTO.setPermissions(permKeys);
            
            return Result.success(userInfoDTO);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return Result.error("获取用户信息失败");
        }
    }
    
    /**
     * 测试邮件发送（仅用于调试）
     */
    @GetMapping("/test-email")
    public Result<?> testEmail(@RequestParam String email) {
        try {
            log.info("测试邮件发送 - 目标邮箱: {}", email);
            String testCode = "123456";
            boolean success = emailService.sendVerifyCode(email, testCode);
            
            if (success) {
                return Result.success("测试邮件发送成功，请检查邮箱");
            } else {
                return Result.error("测试邮件发送失败，请查看日志");
            }
        } catch (Exception e) {
            log.error("测试邮件发送异常", e);
            return Result.error("测试邮件发送异常: " + e.getMessage());
        }
    }
}
