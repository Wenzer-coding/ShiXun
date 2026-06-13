package com.eutmp.app.service;

import com.eutmp.app.bean.Course;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 课程信息Service接口
 */
public interface CourseService {
    
    /**
     * 分页查询课程
     */
    PageResult<Course> selectByPage(Integer pageNum, Integer pageSize, Course course);
    
    /**
     * 根据ID查询课程
     */
    Course selectById(Long id);
    
    /**
     * 查询所有课程
     */
    List<Course> selectAll();
    
    /**
     * 新增课程
     */
    int insert(Course course);
    
    /**
     * 修改课程
     */
    int update(Course course);
    
    /**
     * 删除课程
     */
    int deleteById(Long id);
    
    /**
     * 批量删除课程
     */
    int deleteByIds(Long[] ids);
    
    /**
     * 校验课程编号是否唯一
     */
    boolean checkCourseCodeUnique(Course course);
}
