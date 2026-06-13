package com.eutmp.app.service.impl;

import com.eutmp.app.bean.Course;
import com.eutmp.app.mapper.CourseMapper;
import com.eutmp.app.service.CourseService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程信息Service实现类
 */
@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
    
    @Autowired
    private CourseMapper courseMapper;
    
    @Override
    public PageResult<Course> selectByPage(Integer pageNum, Integer pageSize, Course course) {
        log.info("分页查询课程: pageNum={}, pageSize={}, course={}", pageNum, pageSize, course);
        
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 查询数据
        List<Course> list = courseMapper.selectCourseList(course);
        
        // 包装成PageInfo
        PageInfo<Course> pageInfo = new PageInfo<>(list);
        
        log.info("分页查询结果: total={}, pages={}", pageInfo.getTotal(), pageInfo.getPages());
        
        // 返回统一的分页结果
        return PageResult.of(
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
    }
    
    @Override
    public Course selectById(Long id) {
        return courseMapper.selectCourseById(id);
    }
    
    @Override
    public List<Course> selectAll() {
        return courseMapper.selectCourseList(new Course());
    }
    
    @Override
    public int insert(Course course) {
        log.info("新增课程: courseCode={}, courseName={}", course.getCourseCode(), course.getCourseName());
        
        // 设置默认值
        course.setStatus(course.getStatus() != null ? course.getStatus() : 1);
        course.setCreateTime(LocalDateTime.now());
        course.setUpdateTime(LocalDateTime.now());
        
        // 插入数据库
        int result = courseMapper.insertCourse(course);
        log.info("新增课程结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int update(Course course) {
        log.info("修改课程: id={}, courseCode={}", course.getId(), course.getCourseCode());
        
        // 设置更新时间
        course.setUpdateTime(LocalDateTime.now());
        
        // 更新数据库
        int result = courseMapper.updateCourse(course);
        log.info("修改课程结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int deleteById(Long id) {
        log.info("删除课程: id={}", id);
        
        // 删除数据库记录
        int result = courseMapper.deleteCourseById(id);
        log.info("删除课程结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        log.info("批量删除课程: ids={}", (Object) ids);
        
        // 批量删除数据库记录
        int result = courseMapper.deleteCourseByIds(ids);
        log.info("批量删除课程结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public boolean checkCourseCodeUnique(Course course) {
        Course existCourse = courseMapper.checkCourseCodeUnique(course.getCourseCode());
        if (existCourse != null && !existCourse.getId().equals(course.getId())) {
            return false;
        }
        return true;
    }
}
