package com.eutmp.app.service;

import com.eutmp.app.bean.SysUser;
import com.eutmp.app.bean.dto.RegisterDTO;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 系统用户表Service接口
 */
public interface SysUserService {
    
    /**
     * 分页查询用户
     */
    PageResult<SysUser> selectByPage(Integer pageNum, Integer pageSize, String username, String nickName, Long deptId);
    
    /**
     * 根据ID查询用户
     */
    SysUser selectById(Long id);
    
    /**
     * 根据用户名查询用户
     */
    SysUser selectByUsername(String username);
    
    /**
     * 查询所有用户
     */
    List<SysUser> selectAll();
    
    /**
     * 根据部门ID查询用户
     */
    List<SysUser> selectByDeptId(Long deptId);
    
    /**
     * 新增用户
     */
    int insert(SysUser sysUser);
    
    /**
     * 更新用户
     */
    int update(SysUser sysUser);
    
    /**
     * 删除用户
     */
    int deleteById(Long id);
    
    /**
     * 用户登录
     */
    SysUser login(String username, String password);
    
    /**
     * 用户注册
     */
    boolean register(RegisterDTO registerDTO);
    
    /**
     * 发送密码重置验证码
     */
    boolean sendResetCode(String email);
    
    /**
     * 重置密码
     */
    boolean resetPassword(String email, String verifyCode, String newPassword);
}
