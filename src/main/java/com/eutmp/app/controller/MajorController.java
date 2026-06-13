package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.Major;
import com.eutmp.app.service.MajorService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/major")
public class MajorController {
    
    @Autowired
    private MajorService majorService;
    
    @GetMapping("/list")
    public Result<PageResult<Major>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String majorName,
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) Integer status) {
        try {
            Major major = new Major();
            major.setMajorName(majorName);
            major.setDeptId(deptId);
            major.setStatus(status);
            PageResult<Major> pageResult = majorService.selectByPage(pageNum, pageSize, major);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<Major> getById(@PathVariable Long id) {
        try {
            return Result.success(majorService.selectById(id));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    @OperLog(module = "专业管理", operType = 1)
    public Result<String> insert(@RequestBody Major major) {
        try {
            if (!majorService.checkMajorCodeUnique(major)) {
                return Result.error("专业编码已存在");
            }
            majorService.insert(major);
            return Result.success("新增成功");
        } catch (Exception e) {
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    @PutMapping
    @OperLog(module = "专业管理", operType = 2)
    public Result<String> update(@RequestBody Major major) {
        try {
            if (!majorService.checkMajorCodeUnique(major)) {
                return Result.error("专业编码已存在");
            }
            majorService.update(major);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @OperLog(module = "专业管理", operType = 3)
    public Result<String> delete(@PathVariable Long id) {
        try {
            majorService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/batch")
    @OperLog(module = "专业管理", operType = 3)
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            majorService.deleteByIds(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
