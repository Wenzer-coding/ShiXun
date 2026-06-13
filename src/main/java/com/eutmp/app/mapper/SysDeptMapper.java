package com.eutmp.app.mapper;

import com.eutmp.app.bean.SysDept;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 部门表Mapper接口
 */
@Mapper
public interface SysDeptMapper {
    
    /**
     * 根据ID查询部门
     */
    SysDept selectById(Long id);
    
    /**
     * 查询所有部门
     */
    List<SysDept> selectAll();
    
    /**
     * 根据父部门ID查询
     */
    List<SysDept> selectByParentId(Long parentId);
    
    /**
     * 根据部门名称模糊查询
     */
    List<SysDept> selectByDeptName(String deptName);
    
    /**
     * 新增部门
     */
    int insert(SysDept sysDept);
    
    /**
     * 更新部门
     */
    int update(SysDept sysDept);
    
    /**
     * 删除部门
     */
    int deleteById(Long id);
}
