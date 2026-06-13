package com.eutmp.app.service;

import com.eutmp.app.bean.Student;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 学生信息Service接口
 */
public interface StudentService {
    
    /**
     * 分页查询学生
     */
    PageResult<Student> selectByPage(Integer pageNum, Integer pageSize, Student student);
    
    /**
     * 根据ID查询学生
     */
    Student selectById(Long id);
    
    /**
     * 查询所有学生
     */
    List<Student> selectAll();
    
    /**
     * 新增学生
     */
    int insert(Student student);
    
    /**
     * 修改学生
     */
    int update(Student student);
    
    /**
     * 删除学生
     */
    int deleteById(Long id);
    
    /**
     * 批量删除学生
     */
    int deleteByIds(Long[] ids);
    
    /**
     * 校验学号是否唯一
     */
    boolean checkStudentNoUnique(Student student);
}
