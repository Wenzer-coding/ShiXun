package com.eutmp.app.mapper;

import com.eutmp.app.bean.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 角色权限关联表Mapper接口
 */
@Mapper
public interface SysRolePermissionMapper {
    
    /**
     * 根据ID查询
     */
    SysRolePermission selectById(Long id);
    
    /**
     * 根据角色ID查询
     */
    List<SysRolePermission> selectByRoleId(Long roleId);
    
    /**
     * 根据权限ID查询
     */
    List<SysRolePermission> selectByPermId(Long permId);
    
    /**
     * 新增
     */
    int insert(SysRolePermission sysRolePermission);
    
    /**
     * 批量新增
     */
    int batchInsert(List<SysRolePermission> list);
    
    /**
     * 根据角色ID删除
     */
    int deleteByRoleId(Long roleId);
    
    /**
     * 删除
     */
    int deleteById(Long id);
}
