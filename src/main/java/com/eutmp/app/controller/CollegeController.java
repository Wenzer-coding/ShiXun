package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.College;
import com.eutmp.app.service.CollegeService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/college")
public class CollegeController {
    
    @Autowired
    private CollegeService collegeService;
    
    @GetMapping("/list")
    public Result<PageResult<College>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String collegeName,
            @RequestParam(required = false) Integer status) {
        try {
            College college = new College();
            college.setCollegeName(collegeName);
            college.setStatus(status);
            PageResult<College> pageResult = collegeService.selectByPage(pageNum, pageSize, college);
            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("查询学院列表失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/all")
    public Result<List<College>> getAll() {
        try {
            List<College> list = collegeService.selectAll(new College());
            return Result.success(list);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<College> getById(@PathVariable Long id) {
        try {
            College college = collegeService.selectById(id);
            return Result.success(college);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    @OperLog(module = "学院管理", operType = 1, description = "新增学院")
    public Result<String> insert(@RequestBody College college) {
        try {
            if (!collegeService.checkCollegeCodeUnique(college)) {
                return Result.error("学院编码已存在");
            }
            collegeService.insert(college);
            return Result.success("新增学院成功");
        } catch (Exception e) {
            log.error("新增学院失败", e);
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    @PutMapping
    @OperLog(module = "学院管理", operType = 2, description = "修改学院")
    public Result<String> update(@RequestBody College college) {
        try {
            if (!collegeService.checkCollegeCodeUnique(college)) {
                return Result.error("学院编码已存在");
            }
            collegeService.update(college);
            return Result.success("修改学院成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @OperLog(module = "学院管理", operType = 3, description = "删除学院")
    public Result<String> delete(@PathVariable Long id) {
        try {
            collegeService.deleteById(id);
            return Result.success("删除学院成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/batch")
    @OperLog(module = "学院管理", operType = 3, description = "批量删除学院")
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            collegeService.deleteByIds(ids);
            return Result.success("批量删除学院成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
