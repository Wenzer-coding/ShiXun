package com.eutmp.app.service.impl;

import com.eutmp.app.bean.SysRolePermission;
import com.eutmp.app.mapper.SysRolePermissionMapper;
import com.eutmp.app.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 角色权限关联表Service实现类
 */
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
    
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    
    @Override
    public SysRolePermission selectById(Long id) {
        return sysRolePermissionMapper.selectById(id);
    }
    
    @Override
    public List<SysRolePermission> selectByRoleId(Long roleId) {
        return sysRolePermissionMapper.selectByRoleId(roleId);
    }
    
    @Override
    public List<SysRolePermission> selectByPermId(Long permId) {
        return sysRolePermissionMapper.selectByPermId(permId);
    }
    
    @Override
    public int insert(SysRolePermission sysRolePermission) {
        return sysRolePermissionMapper.insert(sysRolePermission);
    }
    
    @Override
    public int batchInsert(List<SysRolePermission> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return sysRolePermissionMapper.batchInsert(list);
    }
    
    @Override
    public int deleteByRoleId(Long roleId) {
        return sysRolePermissionMapper.deleteByRoleId(roleId);
    }
    
    @Override
    public int deleteById(Long id) {
        return sysRolePermissionMapper.deleteById(id);
    }
}
