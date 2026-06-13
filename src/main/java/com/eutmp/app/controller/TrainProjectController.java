package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.TrainProject;
import com.eutmp.app.service.TrainProjectService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/project")
public class TrainProjectController {
    
    @Autowired
    private TrainProjectService trainProjectService;
    
    /**
     * 查询所有培训项目(用于下拉框)
     */
    @GetMapping("/all")
    public Result<List<TrainProject>> getAll() {
        try {
            return Result.success(trainProjectService.selectAll(new TrainProject()));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<PageResult<TrainProject>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String projectName,
            @RequestParam(required = false) String projectCode,
            @RequestParam(required = false) Integer projectType,
            @RequestParam(required = false) Integer status) {
        try {
            TrainProject project = new TrainProject();
            project.setProjectName(projectName);
            project.setProjectCode(projectCode);
            project.setProjectType(projectType);
            project.setStatus(status);
            return Result.success(trainProjectService.selectByPage(pageNum, pageSize, project));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<TrainProject> getById(@PathVariable Long id) {
        try {
            return Result.success(trainProjectService.selectById(id));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    @OperLog(module = "实训项目", operType = 1)
    public Result<String> insert(@RequestBody TrainProject trainProject) {
        try {
            if (!trainProjectService.checkProjectCodeUnique(trainProject)) {
                return Result.error("项目编号已存在");
            }
            trainProjectService.insert(trainProject);
            return Result.success("新增成功");
        } catch (Exception e) {
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    @PutMapping
    @OperLog(module = "实训项目", operType = 2)
    public Result<String> update(@RequestBody TrainProject trainProject) {
        try {
            if (!trainProjectService.checkProjectCodeUnique(trainProject)) {
                return Result.error("项目编号已存在");
            }
            trainProjectService.update(trainProject);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @OperLog(module = "实训项目", operType = 3)
    public Result<String> delete(@PathVariable Long id) {
        try {
            trainProjectService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/batch")
    @OperLog(module = "实训项目", operType = 3)
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            trainProjectService.deleteByIds(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
