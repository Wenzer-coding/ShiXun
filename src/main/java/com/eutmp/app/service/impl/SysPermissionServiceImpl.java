package com.eutmp.app.service.impl;

import com.eutmp.app.bean.SysPermission;
import com.eutmp.app.mapper.SysPermissionMapper;
import com.eutmp.app.mapper.SysRolePermissionMapper;
import com.eutmp.app.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限资源表Service实现类
 */
@Slf4j
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    
    @Override
    public SysPermission selectById(Long id) {
        return sysPermissionMapper.selectById(id);
    }
    
    @Override
    public List<SysPermission> selectAll() {
        return sysPermissionMapper.selectAll();
    }
    
    @Override
    public List<SysPermission> selectByParentId(Long parentId) {
        return sysPermissionMapper.selectByParentId(parentId);
    }
    
    @Override
    public SysPermission selectByPermKey(String permKey) {
        return sysPermissionMapper.selectByPermKey(permKey);
    }
    
    @Override
    public int insert(SysPermission sysPermission) {
        return sysPermissionMapper.insert(sysPermission);
    }
    
    @Override
    public int update(SysPermission sysPermission) {
        return sysPermissionMapper.update(sysPermission);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        // 检查权限是否被角色引用
        List<com.eutmp.app.bean.SysRolePermission> refs = sysRolePermissionMapper.selectByPermId(id);
        if (refs != null && !refs.isEmpty()) {
            throw new RuntimeException("该权限正在被 " + refs.size() + " 个角色使用，请先解除关联后再删除");
        }
        return sysPermissionMapper.deleteById(id);
    }
    
    @Override
    public List<SysPermission> selectMenuTreeByUserId(Long userId) {
        log.info("=== 查询用户菜单，用户ID: {} ===", userId);
        
        // 根据用户ID查询实际权限（通过用户->角色->权限关联）
        List<SysPermission> allPermissions = sysPermissionMapper.selectByUserId(userId);
        log.info("查询到的所有权限数量（含按钮）: {}", allPermissions != null ? allPermissions.size() : 0);
        
        if (allPermissions != null && !allPermissions.isEmpty()) {
            log.info("权限详情: {}", allPermissions);
        }
        
        // 只返回菜单类型（permType=1）且状态为启用（status=1）的权限
        List<SysPermission> menus = allPermissions.stream()
                .filter(p -> {
                    boolean isMenu = p.getPermType() == 1 && p.getStatus() == 1;
                    if (!isMenu) {
                        log.debug("过滤掉权限: {} (类型={}, 状态={})", p.getPermName(), p.getPermType(), p.getStatus());
                    }
                    return isMenu;
                })
                .sorted((m1, m2) -> {
                    // 先按parentId排序，再按sort排序
                    int parentIdCompare = m1.getParentId().compareTo(m2.getParentId());
                    if (parentIdCompare != 0) {
                        return parentIdCompare;
                    }
                    return m1.getSort().compareTo(m2.getSort());
                })
                .collect(Collectors.toList());
        
        log.info("过滤后的菜单数量: {}", menus.size());
        log.info("菜单列表: {}", menus);
        
        return menus;
    }
    
    @Override
    public List<SysPermission> selectByUserId(Long userId) {
        // 根据用户ID查询所有权限（包括菜单和按钮）
        return sysPermissionMapper.selectByUserId(userId);
    }
}
