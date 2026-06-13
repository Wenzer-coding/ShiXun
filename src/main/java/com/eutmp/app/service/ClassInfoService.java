package com.eutmp.app.service;

import com.eutmp.app.bean.ClassInfo;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 班级信息Service接口
 */
public interface ClassInfoService {
    
    /**
     * 分页查询班级
     */
    PageResult<ClassInfo> selectByPage(Integer pageNum, Integer pageSize, ClassInfo classInfo);
    
    /**
     * 根据ID查询班级
     */
    ClassInfo selectById(Long id);
    
    /**
     * 查询所有班级
     */
    List<ClassInfo> selectAll();
    
    /**
     * 新增班级
     */
    int insert(ClassInfo classInfo);
    
    /**
     * 修改班级
     */
    int update(ClassInfo classInfo);
    
    /**
     * 删除班级
     */
    int deleteById(Long id);
    
    /**
     * 批量删除班级
     */
    int deleteByIds(Long[] ids);
    
    /**
     * 校验班级编号是否唯一
     */
    boolean checkClassCodeUnique(ClassInfo classInfo);
}
