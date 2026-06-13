package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.ClassInfo;
import com.eutmp.app.service.ClassInfoService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 班级管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/class")
public class ClassInfoController {
    
    @Autowired
    private ClassInfoService classInfoService;
    
    /**
     * 分页查询班级
     */
    @GetMapping("/page")
    public Result<PageResult<ClassInfo>> getPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String classCode,
            @RequestParam(required = false) String major,
            @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) String headTeacher,
            @RequestParam(required = false) Integer status) {
        try {
            ClassInfo classInfo = new ClassInfo();
            classInfo.setClassName(className);
            classInfo.setClassCode(classCode);
            classInfo.setMajor(major);
            classInfo.setGrade(grade);
            classInfo.setHeadTeacher(headTeacher);
            classInfo.setStatus(status);
            
            PageResult<ClassInfo> pageResult = classInfoService.selectByPage(pageNum, pageSize, classInfo);
            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("分页查询班级失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询班级
     */
    @GetMapping("/{id}")
    public Result<ClassInfo> getById(@PathVariable Long id) {
        try {
            ClassInfo classInfo = classInfoService.selectById(id);
            return Result.success(classInfo);
        } catch (Exception e) {
            log.error("查询班级失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 新增班级
     */
    @PostMapping
    @OperLog(module = "班级管理", operType = 1, description = "新增班级")
    public Result<String> insert(@RequestBody ClassInfo classInfo) {
        try {
            // 校验班级编号是否唯一
            if (!classInfoService.checkClassCodeUnique(classInfo)) {
                return Result.error("班级编号已存在");
            }
            
            classInfoService.insert(classInfo);
            return Result.success("新增班级成功");
        } catch (Exception e) {
            log.error("新增班级失败", e);
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改班级
     */
    @PutMapping
    @OperLog(module = "班级管理", operType = 2, description = "修改班级")
    public Result<String> update(@RequestBody ClassInfo classInfo) {
        try {
            // 校验班级编号是否唯一
            if (!classInfoService.checkClassCodeUnique(classInfo)) {
                return Result.error("班级编号已存在");
            }
            
            classInfoService.update(classInfo);
            return Result.success("修改班级成功");
        } catch (Exception e) {
            log.error("修改班级失败", e);
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除班级
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "班级管理", operType = 3, description = "删除班级")
    public Result<String> delete(@PathVariable Long id) {
        try {
            classInfoService.deleteById(id);
            return Result.success("删除班级成功");
        } catch (Exception e) {
            log.error("删除班级失败", e);
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量删除班级
     */
    @DeleteMapping("/batch")
    @OperLog(module = "班级管理", operType = 3, description = "批量删除班级")
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            if (ids == null || ids.length == 0) {
                return Result.error("请选择要删除的班级");
            }
            classInfoService.deleteByIds(ids);
            return Result.success("批量删除班级成功");
        } catch (Exception e) {
            log.error("批量删除班级失败", e);
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
