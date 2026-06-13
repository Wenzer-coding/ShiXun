package com.eutmp.app.mapper;

import com.eutmp.app.bean.SysRoleDept;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 角色部门关联表Mapper接口
 */
@Mapper
public interface SysRoleDeptMapper {
    
    /**
     * 根据ID查询
     */
    SysRoleDept selectById(Long id);
    
    /**
     * 根据角色ID查询
     */
    List<SysRoleDept> selectByRoleId(Long roleId);
    
    /**
     * 根据部门ID查询
     */
    List<SysRoleDept> selectByDeptId(Long deptId);
    
    /**
     * 新增
     */
    int insert(SysRoleDept sysRoleDept);
    
    /**
     * 批量新增
     */
    int batchInsert(List<SysRoleDept> list);
    
    /**
     * 根据角色ID删除
     */
    int deleteByRoleId(Long roleId);
    
    /**
     * 删除
     */
    int deleteById(Long id);
}
