package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.Course;
import com.eutmp.app.service.CourseService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 课程管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/course")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    /**
     * 分页查询课程
     */
    @GetMapping("/page")
    public Result<PageResult<Course>> getPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String courseCode,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String teacher,
            @RequestParam(required = false) Integer courseType,
            @RequestParam(required = false) Integer status) {
        try {
            Course course = new Course();
            course.setCourseCode(courseCode);
            course.setCourseName(courseName);
            course.setTeacher(teacher);
            course.setCourseType(courseType);
            course.setStatus(status);
            
            PageResult<Course> pageResult = courseService.selectByPage(pageNum, pageSize, course);
            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("分页查询课程失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询课程
     */
    @GetMapping("/{id}")
    public Result<Course> getById(@PathVariable Long id) {
        try {
            Course course = courseService.selectById(id);
            return Result.success(course);
        } catch (Exception e) {
            log.error("查询课程失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 新增课程
     */
    @PostMapping
    @OperLog(module = "课程管理", operType = 1, description = "新增课程")
    public Result<String> insert(@RequestBody Course course) {
        try {
            // 校验课程编号是否唯一
            if (!courseService.checkCourseCodeUnique(course)) {
                return Result.error("课程编号已存在");
            }
            
            courseService.insert(course);
            return Result.success("新增课程成功");
        } catch (Exception e) {
            log.error("新增课程失败", e);
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改课程
     */
    @PutMapping
    @OperLog(module = "课程管理", operType = 2, description = "修改课程")
    public Result<String> update(@RequestBody Course course) {
        try {
            // 校验课程编号是否唯一
            if (!courseService.checkCourseCodeUnique(course)) {
                return Result.error("课程编号已存在");
            }
            
            courseService.update(course);
            return Result.success("修改课程成功");
        } catch (Exception e) {
            log.error("修改课程失败", e);
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除课程
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "课程管理", operType = 3, description = "删除课程")
    public Result<String> delete(@PathVariable Long id) {
        try {
            courseService.deleteById(id);
            return Result.success("删除课程成功");
        } catch (Exception e) {
            log.error("删除课程失败", e);
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量删除课程
     */
    @DeleteMapping("/batch")
    @OperLog(module = "课程管理", operType = 3, description = "批量删除课程")
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            if (ids == null || ids.length == 0) {
                return Result.error("请选择要删除的课程");
            }
            courseService.deleteByIds(ids);
            return Result.success("批量删除课程成功");
        } catch (Exception e) {
            log.error("批量删除课程失败", e);
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
