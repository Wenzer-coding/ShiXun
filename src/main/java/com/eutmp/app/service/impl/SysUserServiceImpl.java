package com.eutmp.app.service.impl;

import com.eutmp.app.bean.SysUser;
import com.eutmp.app.bean.dto.RegisterDTO;
import com.eutmp.app.mapper.SysUserMapper;
import com.eutmp.app.service.EmailService;
import com.eutmp.app.service.SysUserService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eutmp.app.utils.DateUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 系统用户表Service实现类
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // 验证码存储 (实际项目应使用 Redis)
    private static final Map<String, VerifyCodeInfo> codeStorage = new ConcurrentHashMap<>();
    
    /**
     * 验证码信息
     */
    private static class VerifyCodeInfo {
        String code;
        LocalDateTime expireTime;
        
        VerifyCodeInfo(String code, LocalDateTime expireTime) {
            this.code = code;
            this.expireTime = expireTime;
        }
    }
    
    @Override
    public PageResult<SysUser> selectByPage(Integer pageNum, Integer pageSize, String username, String nickName, Long deptId) {
        log.info("分页查询用户: pageNum={}, pageSize={}, username={}, nickName={}, deptId={}", 
                pageNum, pageSize, username, nickName, deptId);
        
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 查询数据
        List<SysUser> list = sysUserMapper.selectByCondition(username, nickName, deptId);
        
        // 包装成PageInfo
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        
        log.info("分页查询结果: total={}, pages={}", pageInfo.getTotal(), pageInfo.getPages());
        
        // 返回统一的分页结果
        return PageResult.of(
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
    }
    
    @Override
    public SysUser selectById(Long id) {
        return sysUserMapper.selectById(id);
    }
    
    @Override
    public SysUser selectByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }
    
    @Override
    public List<SysUser> selectAll() {
        return sysUserMapper.selectAll();
    }
    
    @Override
    public List<SysUser> selectByDeptId(Long deptId) {
        return sysUserMapper.selectByDeptId(deptId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(SysUser sysUser) {
        log.info("新增用户: username={}, nickName={}, deptId={}", 
                sysUser.getUsername(), sysUser.getNickName(), sysUser.getDeptId());
        
        // 设置默认值
        sysUser.setStatus(sysUser.getStatus() != null ? sysUser.getStatus() : 1);
        String now = DateUtils.now();
        sysUser.setCreateTime(now);
        sysUser.setUpdateTime(now);
        
        // 插入数据库
        int result = sysUserMapper.insert(sysUser);
        log.info("新增用户结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysUser sysUser) {
        log.info("更新用户: id={}, username={}", sysUser.getId(), sysUser.getUsername());
        
        // 检查是否是admin用户
        SysUser existUser = sysUserMapper.selectById(sysUser.getId());
        if (existUser != null && "admin".equals(existUser.getUsername())) {
            // admin用户只允许修改密码，不允许修改其他信息
            // 如果尝试修改密码以外的字段，需要特殊处理
            log.info("admin用户更新，只允许修改密码");
            
            // 如果提供了角色ID，需要检查是否包含超级管理员角色(role_id=2)
            if (sysUser.getRoleIds() != null && !sysUser.getRoleIds().isEmpty()) {
                boolean hasAdminRole = sysUser.getRoleIds().contains(2L);
                if (!hasAdminRole) {
                    log.error("不允许去掉admin用户的超级管理员角色");
                    throw new RuntimeException("不允许去掉admin用户的超级管理员角色，这是系统安全保护机制");
                }
            }
        }
        
        // 设置更新时间
        sysUser.setUpdateTime(DateUtils.now());
        
        // 更新数据库
        int result = sysUserMapper.update(sysUser);
        log.info("更新用户结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        log.info("删除用户: id={}", id);
        
        // 检查是否是admin用户
        SysUser user = sysUserMapper.selectById(id);
        if (user != null && "admin".equals(user.getUsername())) {
            log.error("不允许删除admin用户: id={}", id);
            throw new RuntimeException("不允许删除admin用户，这是系统安全保护机制");
        }
        
        // 删除数据库记录
        int result = sysUserMapper.deleteById(id);
        log.info("删除用户结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public SysUser login(String username, String password) {
        log.info("用户尝试登录 - 用户名: {}", username);
        
        // 查询用户
        SysUser user = sysUserMapper.selectByUsername(username);
        
        if (user == null) {
            log.warn("用户不存在: {}", username);
            return null;
        }
        
        log.info("用户存在，验证密码...");

        // BCrypt密码验证（兼容明文迁移）
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // 尝试明文匹配（兼容旧数据）
            if (!password.equals(user.getPassword())) {
                log.warn("密码错误 - 用户名: {}", username);
                return null;
            }
            // 明文匹配成功，升级为BCrypt加密
            user.setPassword(passwordEncoder.encode(password));
            sysUserMapper.update(user);
        }
        
        log.info("登录成功: {}", username);
        return user;
    }
    
    @Override
    public boolean register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        SysUser existUser = sysUserMapper.selectByUsername(registerDTO.getUsername());
        if (existUser != null) {
            return false;
        }
        
        // 创建新用户
        SysUser user = new SysUser();
        user.setUsername(registerDTO.getUsername());
        user.setNickName(registerDTO.getNickName());
        user.setEmail(registerDTO.getEmail());

        // BCrypt加密存储
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        
        user.setStatus(1); // 默认启用
        String now = DateUtils.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        
        // 插入数据库
        int result = sysUserMapper.insert(user);
        return result > 0;
    }
    
    @Override
    public boolean sendResetCode(String email) {
        log.info("请求发送重置密码验证码 - 邮箱: {}", email);
        
        // 通过邮箱查找用户
        List<SysUser> allUsers = sysUserMapper.selectAll();
        SysUser user = allUsers.stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
        
        if (user == null) {
            log.warn("该邮箱未注册: {}", email);
            return false;
        }
        
        // 生成6位验证码
        String code = String.format("%06d", new Random().nextInt(999999));
        
        // 存储验证码，5分钟过期
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(5);
        codeStorage.put(email, new VerifyCodeInfo(code, expireTime));
        
        // 发送邮件
        boolean sent = emailService.sendVerifyCode(email, code);
        
        if (sent) {
            log.info("验证码已生成并发送 - 邮箱: {}, 验证码: {}", email, code);
            return true;
        } else {
            log.error("验证码邮件发送失败 - 邮箱: {}", email);
            // 发送失败，清除验证码
            codeStorage.remove(email);
            return false;
        }
    }
    
    @Override
    public boolean resetPassword(String email, String verifyCode, String newPassword) {
        // 验证验证码
        VerifyCodeInfo codeInfo = codeStorage.get(email);
        if (codeInfo == null) {
            return false;
        }
        
        // 检查是否过期
        if (LocalDateTime.now().isAfter(codeInfo.expireTime)) {
            codeStorage.remove(email);
            return false;
        }
        
        // 验证验证码是否正确
        if (!codeInfo.code.equals(verifyCode)) {
            return false;
        }
        
        // 查找用户
        SysUser user = sysUserMapper.selectAll().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
        
        if (user == null) {
            return false;
        }
        
        // 更新密码 - BCrypt加密存储
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdateTime(DateUtils.now());
        
        int result = sysUserMapper.update(user);
        
        // 删除已使用的验证码
        codeStorage.remove(email);
        
        return result > 0;
    }
}
