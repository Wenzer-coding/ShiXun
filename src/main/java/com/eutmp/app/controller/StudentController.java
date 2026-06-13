package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.Student;
import com.eutmp.app.service.StudentService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    /**
     * 分页查询学生
     */
    @GetMapping("/page")
    public Result<PageResult<Student>> getPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String studentNo,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Integer enrollYear,
            @RequestParam(required = false) Integer status) {
        try {
            Student student = new Student();
            student.setStudentNo(studentNo);
            student.setStudentName(studentName);
            student.setClassId(classId);
            student.setEnrollYear(enrollYear);
            student.setStatus(status);
            
            PageResult<Student> pageResult = studentService.selectByPage(pageNum, pageSize, student);
            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("分页查询学生失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询所有学生(供下拉选择)
     */
    @GetMapping("/all")
    public Result<List<Student>> getAll() {
        try {
            List<Student> list = studentService.selectAll();
            return Result.success(list);
        } catch (Exception e) {
            log.error("查询学生列表失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询学生
     */
    @GetMapping("/{id}")
    public Result<Student> getById(@PathVariable Long id) {
        try {
            Student student = studentService.selectById(id);
            return Result.success(student);
        } catch (Exception e) {
            log.error("查询学生失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 新增学生
     */
    @PostMapping
    @OperLog(module = "学生管理", operType = 1, description = "新增学生")
    public Result<String> insert(@RequestBody Student student) {
        try {
            // 校验学号是否唯一
            if (!studentService.checkStudentNoUnique(student)) {
                return Result.error("学号已存在");
            }
            
            studentService.insert(student);
            return Result.success("新增学生成功");
        } catch (Exception e) {
            log.error("新增学生失败", e);
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改学生
     */
    @PutMapping
    @OperLog(module = "学生管理", operType = 2, description = "修改学生")
    public Result<String> update(@RequestBody Student student) {
        try {
            // 校验学号是否唯一
            if (!studentService.checkStudentNoUnique(student)) {
                return Result.error("学号已存在");
            }
            
            studentService.update(student);
            return Result.success("修改学生成功");
        } catch (Exception e) {
            log.error("修改学生失败", e);
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除学生
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "学生管理", operType = 3, description = "删除学生")
    public Result<String> delete(@PathVariable Long id) {
        try {
            studentService.deleteById(id);
            return Result.success("删除学生成功");
        } catch (Exception e) {
            log.error("删除学生失败", e);
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量删除学生
     */
    @DeleteMapping("/batch")
    @OperLog(module = "学生管理", operType = 3, description = "批量删除学生")
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            if (ids == null || ids.length == 0) {
                return Result.error("请选择要删除的学生");
            }
            studentService.deleteByIds(ids);
            return Result.success("批量删除学生成功");
        } catch (Exception e) {
            log.error("批量删除学生失败", e);
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
