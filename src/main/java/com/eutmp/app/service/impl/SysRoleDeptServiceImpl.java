package com.eutmp.app.service.impl;

import com.eutmp.app.bean.SysRoleDept;
import com.eutmp.app.mapper.SysRoleDeptMapper;
import com.eutmp.app.service.SysRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 角色部门关联表Service实现类
 */
@Service
public class SysRoleDeptServiceImpl implements SysRoleDeptService {

    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    public SysRoleDept selectById(Long id) {
        return sysRoleDeptMapper.selectById(id);
    }

    @Override
    public List<SysRoleDept> selectByRoleId(Long roleId) {
        return sysRoleDeptMapper.selectByRoleId(roleId);
    }

    @Override
    public List<SysRoleDept> selectByDeptId(Long deptId) {
        return sysRoleDeptMapper.selectByDeptId(deptId);
    }

    @Override
    public int insert(SysRoleDept sysRoleDept) {
        return sysRoleDeptMapper.insert(sysRoleDept);
    }

    @Override
    public int batchInsert(List<SysRoleDept> list) {
        return sysRoleDeptMapper.batchInsert(list);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        return sysRoleDeptMapper.deleteByRoleId(roleId);
    }

    @Override
    public int deleteById(Long id) {
        return sysRoleDeptMapper.deleteById(id);
    }
}
