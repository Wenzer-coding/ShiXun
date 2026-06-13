package com.eutmp.app.mapper;

import com.eutmp.app.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表Mapper接口
 */
@Mapper
public interface SysRoleMapper {
    
    /**
     * 根据ID查询角色
     */
    SysRole selectById(Long id);
    
    /**
     * 查询所有角色
     */
    List<SysRole> selectAll();
    
    /**
     * 根据角色编码查询
     */
    SysRole selectByRoleCode(String roleCode);
    
    /**
     * 新增角色
     */
    int insert(SysRole sysRole);

    /**
     * 条件查询角色（支持分页）
     */
    List<SysRole> selectByCondition(@Param("roleName") String roleName, @Param("status") Integer status);
    
    /**
     * 更新角色
     */
    int update(SysRole sysRole);
    
    /**
     * 删除角色
     */
    int deleteById(Long id);
}
