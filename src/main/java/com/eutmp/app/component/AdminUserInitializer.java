package com.eutmp.app.component;

import com.eutmp.app.bean.SysUser;
import com.eutmp.app.bean.SysUserRole;
import com.eutmp.app.mapper.SysUserMapper;
import com.eutmp.app.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 系统启动时初始化超级管理员用户
 */
@Slf4j
@Component
public class AdminUserInitializer implements CommandLineRunner {
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    
    /**
     * 超级管理员用户名
     */
    private static final String ADMIN_USERNAME = "admin";
    
    /**
     * 超级管理员密码
     */
    private static final String ADMIN_PASSWORD = new BCryptPasswordEncoder().encode("123456");
    
    /**
     * 超级管理员昵称
     */
    private static final String ADMIN_NICK_NAME = "超级管理员";
    
    /**
     * 超级管理员角色ID
     */
    private static final Long ADMIN_ROLE_ID = 2L;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("========== 开始检查超级管理员用户 ==========");
        
        try {
            // 1. 检查admin用户是否存在
            SysUser adminUser = sysUserMapper.selectByUsername(ADMIN_USERNAME);
            
            if (adminUser != null) {
                log.info("超级管理员用户已存在: username={}", ADMIN_USERNAME);
                
                // 检查是否只有一个admin用户
                checkAdminUserCount();

                // 迁移所有明文密码到BCrypt
                migratePasswords();
                
                // 检查admin用户是否有超级管理员角色
                checkAdminRole(adminUser.getId());
            } else {
                log.warn("超级管理员用户不存在，开始初始化...");
                initAdminUser();
            }
            
            log.info("========== 超级管理员用户检查完成 ==========");
        } catch (Exception e) {
            log.error("检查超级管理员用户失败", e);
        }
    }
    
    /**
     * 初始化超级管理员用户
     */
    private void initAdminUser() {
        try {
            // 1. 创建admin用户
            SysUser adminUser = new SysUser();
            adminUser.setUsername(ADMIN_USERNAME);
            adminUser.setPassword(ADMIN_PASSWORD);
            adminUser.setNickName(ADMIN_NICK_NAME);
            adminUser.setPhone("");
            adminUser.setEmail("");
            adminUser.setDeptId(null);
            adminUser.setStatus(1);
            
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            adminUser.setCreateTime(now);
            adminUser.setUpdateTime(now);
            
            int result = sysUserMapper.insert(adminUser);
            
            if (result > 0) {
                log.info("超级管理员用户创建成功: id={}, username={}", adminUser.getId(), ADMIN_USERNAME);
                
                // 2. 分配超级管理员角色
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(adminUser.getId());
                userRole.setRoleId(ADMIN_ROLE_ID);
                
                int roleResult = sysUserRoleMapper.insert(userRole);
                
                if (roleResult > 0) {
                    log.info("超级管理员角色分配成功: userId={}, roleId={}", adminUser.getId(), ADMIN_ROLE_ID);
                } else {
                    log.error("超级管理员角色分配失败");
                }
            } else {
                log.error("超级管理员用户创建失败");
            }
        } catch (Exception e) {
            log.error("初始化超级管理员用户失败", e);
        }
    }
    
    /**
     * 检查是否只有一个admin用户
     */
    private void checkAdminUserCount() {
        List<SysUser> allUsers = sysUserMapper.selectAll();
        long adminCount = allUsers.stream()
                .filter(u -> ADMIN_USERNAME.equals(u.getUsername()))
                .count();
        
        if (adminCount > 1) {
            log.warn("发现 {} 个admin用户，系统只允许有一个admin用户！", adminCount);
            // 这里可以添加清理逻辑，保留第一个，删除其他的
        } else if (adminCount == 1) {
            log.info("admin用户数量正常: 1个");
        }
    }
    
    /**
     * 将数据库中所有明文密码迁移为BCrypt加密
     */
    private void migratePasswords() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        List<SysUser> allUsers = sysUserMapper.selectAll();
        for (SysUser user : allUsers) {
            String pwd = user.getPassword();
            // 非BCrypt哈希(不以$2a/$2b/$2y开头)即为明文，需要迁移
            if (pwd != null && !pwd.startsWith("$2a$") && !pwd.startsWith("$2b$") && !pwd.startsWith("$2y$")) {
                user.setPassword(encoder.encode(pwd));
                sysUserMapper.update(user);
                log.info("用户密码已迁移为BCrypt: username={}", user.getUsername());
            }
        }
    }

    /**
     * 检查admin用户是否有超级管理员角色
     */
    private void checkAdminRole(Long adminUserId) {
        List<SysUserRole> userRoles = sysUserRoleMapper.selectByUserId(adminUserId);
        
        boolean hasAdminRole = userRoles.stream()
                .anyMatch(ur -> ADMIN_ROLE_ID.equals(ur.getRoleId()));
        
        if (!hasAdminRole) {
            log.warn("admin用户缺少超级管理员角色，正在添加...");
            
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(adminUserId);
            userRole.setRoleId(ADMIN_ROLE_ID);
            
            int result = sysUserRoleMapper.insert(userRole);
            
            if (result > 0) {
                log.info("超级管理员角色添加成功");
            } else {
                log.error("超级管理员角色添加失败");
            }
        } else {
            log.info("admin用户已拥有超级管理员角色");
        }
    }
}
