package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.SysDept;
import com.eutmp.app.service.SysDeptService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 查询部门树形结构（不分页，用于树形展示）
     */
    @GetMapping("/tree")
    public Result<List<SysDept>> getDeptTree() {
        // 查询所有部门
        List<SysDept> deptList = sysDeptService.selectAll();
        // 构建树形结构
        List<SysDept> treeList = sysDeptService.buildDeptTree(deptList);
        return Result.success(treeList);
    }

    /**
     * 分页查询部门
     */
    @GetMapping("/page")
    public Result<PageResult<SysDept>> getPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String deptName) {
        PageResult<SysDept> pageResult = sysDeptService.selectByPage(pageNum, pageSize, deptName);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询部门
     */
    @GetMapping("/{id}")
    public Result<SysDept> getById(@PathVariable Long id) {
        SysDept dept = sysDeptService.selectById(id);
        if (dept == null) {
            return Result.error("部门不存在");
        }
        return Result.success(dept);
    }

    /**
     * 查询所有部门
     */
    @GetMapping("/list")
    public Result<List<SysDept>> list() {
        List<SysDept> deptList = sysDeptService.selectAll();
        return Result.success(deptList);
    }

    /**
     * 获取可选学院列表(用于专业管理的学院下拉框)
     */
    @GetMapping("/colleges")
    public Result<List<SysDept>> getColleges() {
        List<SysDept> allDepts = sysDeptService.selectAll();
        // 学院是 parent_id=0 的顶级部门，排除公司(id=20)
        List<Long> collegeIds = allDepts.stream()
            .filter(d -> d.getParentId() == 0L && !d.getId().equals(20L))
            .map(SysDept::getId)
            .collect(Collectors.toList());
        // 返回学院下的子部门(专业实际关联的层级)
        List<SysDept> result = allDepts.stream()
            .filter(d -> collegeIds.contains(d.getParentId()))
            .collect(Collectors.toList());
        return Result.success(result);
    }

    /**
     * 根据部门名称搜索
     */
    @GetMapping("/search")
    public Result<List<SysDept>> search(@RequestParam(required = false) String deptName) {
        List<SysDept> deptList;
        if (deptName != null && !deptName.trim().isEmpty()) {
            // 有关键词，使用LIKE查询
            deptList = sysDeptService.selectByDeptName(deptName.trim());
        } else {
            // 无关键词，查询全部
            deptList = sysDeptService.selectAll();
        }
        return Result.success(deptList);
    }

    /**
     * 根据父部门ID查询
     */
    @GetMapping("/parent/{parentId}")
    public Result<List<SysDept>> getByParentId(@PathVariable Long parentId) {
        List<SysDept> deptList = sysDeptService.selectByParentId(parentId);
        return Result.success(deptList);
    }

    /**
     * 新增部门
     */
    @PostMapping
    @OperLog(module = "部门管理", operType = 1, description = "新增部门")
    public Result<String> insert(@RequestBody SysDept sysDept) {
        sysDeptService.insert(sysDept);
        return Result.success("新增部门成功", null);
    }

    /**
     * 更新部门
     */
    @PutMapping
    @OperLog(module = "部门管理", operType = 2, description = "修改部门")
    public Result<String> update(@RequestBody SysDept sysDept) {
        sysDeptService.update(sysDept);
        return Result.success("修改部门成功", null);
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "部门管理", operType = 3, description = "删除部门")
    public Result<String> delete(@PathVariable Long id) {
        sysDeptService.deleteById(id);
        return Result.success("删除部门成功", null);
    }
}
