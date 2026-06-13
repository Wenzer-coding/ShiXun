package com.eutmp.app.service;

import com.eutmp.app.bean.College;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 学院信息Service接口
 */
public interface CollegeService {
    
    /**
     * 分页查询学院
     */
    PageResult<College> selectByPage(Integer pageNum, Integer pageSize, College college);
    
    /**
     * 根据ID查询学院
     */
    College selectById(Long id);
    
    /**
     * 查询所有学院
     */
    List<College> selectAll(College college);
    
    /**
     * 新增学院
     */
    int insert(College college);
    
    /**
     * 修改学院
     */
    int update(College college);
    
    /**
     * 删除学院
     */
    int deleteById(Long id);
    
    /**
     * 批量删除学院
     */
    int deleteByIds(Long[] ids);
    
    /**
     * 校验学院编码是否唯一
     */
    boolean checkCollegeCodeUnique(College college);
}
