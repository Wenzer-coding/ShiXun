package com.eutmp.app.mapper;

import com.eutmp.app.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 系统用户表Mapper接口
 */
@Mapper
public interface SysUserMapper {
    
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
     * 条件查询用户（支持分页）
     */
    List<SysUser> selectByCondition(@Param("username") String username,
                                     @Param("nickName") String nickName,
                                     @Param("deptId") Long deptId);
    
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
}
