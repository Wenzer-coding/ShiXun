package com.eutmp.app.service;

import com.eutmp.app.bean.SysDept;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 部门表Service接口
 */
public interface SysDeptService {
    
    /**
     * 根据ID查询部门
     */
    SysDept selectById(Long id);
    
    /**
     * 查询所有部门
     */
    List<SysDept> selectAll();
    
    /**
     * 分页查询部门
     */
    PageResult<SysDept> selectByPage(Integer pageNum, Integer pageSize, String deptName);
    
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
    
    /**
     * 构建部门树形结构
     */
    List<SysDept> buildDeptTree(List<SysDept> deptList);
}
