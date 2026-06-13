package com.eutmp.app.service.impl;

import com.eutmp.app.bean.SysRole;
import com.eutmp.app.bean.SysUserRole;
import com.eutmp.app.mapper.SysRoleMapper;
import com.eutmp.app.mapper.SysUserRoleMapper;
import com.eutmp.app.service.SysRoleService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eutmp.app.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.StringUtils;
import java.util.List;

/**
 * 角色表Service实现类
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {
    
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysRole selectById(Long id) {
        log.debug("根据ID查询角色: {}", id);
        return sysRoleMapper.selectById(id);
    }
    
    @Override
    public List<SysRole> selectAll() {
        log.debug("查询所有角色");
        return sysRoleMapper.selectAll();
    }
    
    @Override
    public PageResult<SysRole> selectByPage(int pageNum, int pageSize, String roleName, Integer status) {
        log.debug("分页查询角色: pageNum={}, pageSize={}, roleName={}, status={}", pageNum, pageSize, roleName, status);
        
        // 开启分页（在SQL层面过滤，避免分页结果错误）
        PageHelper.startPage(pageNum, pageSize);

        // 查询数据（过滤条件在SQL中处理）
        List<SysRole> list = sysRoleMapper.selectByCondition(roleName, status);

        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        
        return PageResult.of(pageNum, pageSize, pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public SysRole selectByRoleCode(String roleCode) {
        log.debug("根据角色编码查询: {}", roleCode);
        return sysRoleMapper.selectByRoleCode(roleCode);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(SysRole sysRole) {
        log.debug("新增角色: {}", sysRole.getRoleName());
        
        // 检查角色编码是否已存在
        SysRole existRole = sysRoleMapper.selectByRoleCode(sysRole.getRoleCode());
        if (existRole != null) {
            throw new RuntimeException("角色编码已存在: " + sysRole.getRoleCode());
        }
        
        // 设置时间
        String now = DateUtils.now();
        sysRole.setCreateTime(now);
        sysRole.setUpdateTime(now);
        
        // 默认状态
        if (sysRole.getStatus() == null) {
            sysRole.setStatus(1);
        }
        
        return sysRoleMapper.insert(sysRole);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysRole sysRole) {
        log.debug("更新角色: {}", sysRole.getId());
        
        // 检查角色是否存在
        SysRole existRole = sysRoleMapper.selectById(sysRole.getId());
        if (existRole == null) {
            throw new RuntimeException("角色不存在");
        }
        
        // 如果修改了角色编码，检查新编码是否已被使用
        if (StringUtils.hasText(sysRole.getRoleCode()) && !sysRole.getRoleCode().equals(existRole.getRoleCode())) {
            SysRole codeExist = sysRoleMapper.selectByRoleCode(sysRole.getRoleCode());
            if (codeExist != null && !codeExist.getId().equals(sysRole.getId())) {
                throw new RuntimeException("角色编码已存在: " + sysRole.getRoleCode());
            }
        }
        
        // 设置更新时间
        sysRole.setUpdateTime(DateUtils.now());
        
        return sysRoleMapper.update(sysRole);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        log.debug("删除角色: {}", id);
        
        // 检查角色是否存在
        SysRole existRole = sysRoleMapper.selectById(id);
        if (existRole == null) {
            throw new RuntimeException("角色不存在，可能已被删除");
        }
        
        // 检查是否是系统内置角色
        if ("admin".equals(existRole.getRoleCode())) {
            throw new RuntimeException("超级管理员角色不允许删除，这是系统安全保护机制");
        }
        
        // 检查角色是否被用户使用
        List<SysUserRole> userRoles = sysUserRoleMapper.selectByRoleId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            throw new RuntimeException("该角色正在被 " + userRoles.size() + " 个用户使用，请先解除用户关联后再删除");
        }
        
        return sysRoleMapper.deleteById(id);
    }
    
    @Override
    public boolean isRoleUsedByUsers(Long roleId) {
        log.debug("检查角色是否被用户使用: {}", roleId);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectByRoleId(roleId);
        return userRoles != null && !userRoles.isEmpty();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteByIds(List<Long> ids) {
        log.debug("批量删除角色: {}", ids);
        
        if (ids == null || ids.isEmpty()) {
            throw new RuntimeException("请选择要删除的角色");
        }
        
        int count = 0;
        for (Long id : ids) {
            try {
                count += deleteById(id);
            } catch (Exception e) {
                log.warn("删除角色ID={}失败: {}", id, e.getMessage());
            }
        }
        
        return count;
    }
}
