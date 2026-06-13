package com.eutmp.app.service.impl;

import com.eutmp.app.bean.SysUserRole;
import com.eutmp.app.mapper.SysUserRoleMapper;
import com.eutmp.app.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色关联表Service实现类
 */
@Slf4j
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    
    @Override
    public SysUserRole selectById(Long id) {
        log.debug("根据ID查询用户角色关联: {}", id);
        return sysUserRoleMapper.selectById(id);
    }
    
    @Override
    public List<SysUserRole> selectByUserId(Long userId) {
        log.debug("根据用户ID查询角色关联: {}", userId);
        List<SysUserRole> result = sysUserRoleMapper.selectByUserId(userId);
        return result != null ? result : new ArrayList<>();
    }
    
    @Override
    public List<SysUserRole> selectByRoleId(Long roleId) {
        log.debug("根据角色ID查询用户关联: {}", roleId);
        List<SysUserRole> result = sysUserRoleMapper.selectByRoleId(roleId);
        return result != null ? result : new ArrayList<>();
    }
    
    @Override
    public int insert(SysUserRole sysUserRole) {
        log.debug("新增用户角色关联: userId={}, roleId={}", sysUserRole.getUserId(), sysUserRole.getRoleId());
        return sysUserRoleMapper.insert(sysUserRole);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsert(List<SysUserRole> list) {
        if (list != null && !list.isEmpty()) {
            log.debug("批量新增用户角色关联: {} 条", list.size());
            return sysUserRoleMapper.batchInsert(list);
        }
        return 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByUserId(Long userId) {
        log.debug("根据用户ID删除角色关联: {}", userId);
        return sysUserRoleMapper.deleteByUserId(userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteByUserIdAndRoleIds(Long userId, List<Long> roleIds) {
        log.debug("根据用户ID和角色ID列表批量删除: userId={}, roleIds={}", userId, roleIds);
        if (roleIds == null || roleIds.isEmpty()) {
            return 0;
        }
        return sysUserRoleMapper.batchDeleteByUserIdAndRoleIds(userId, roleIds);
    }
    
    @Override
    public int deleteById(Long id) {
        log.debug("根据ID删除用户角色关联: {}", id);
        return sysUserRoleMapper.deleteById(id);
    }
}
