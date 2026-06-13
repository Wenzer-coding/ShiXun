package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.SysPermission;
import com.eutmp.app.service.SysPermissionService;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限资源表控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 根据ID查询权限
     */
    @GetMapping("/{id}")
    public Result<SysPermission> getById(@PathVariable Long id) {
        SysPermission permission = sysPermissionService.selectById(id);
        if (permission != null) {
            return Result.success(permission);
        } else {
            return Result.error("权限不存在");
        }
    }

    /**
     * 查询所有权限
     */
    @GetMapping("/list")
    public Result<List<SysPermission>> list() {
        List<SysPermission> permissions = sysPermissionService.selectAll();
        return Result.success(permissions);
    }

    /**
     * 根据父权限ID查询
     */
    @GetMapping("/parent/{parentId}")
    public Result<List<SysPermission>> getByParentId(@PathVariable Long parentId) {
        List<SysPermission> permissions = sysPermissionService.selectByParentId(parentId);
        return Result.success(permissions);
    }

    /**
     * 根据权限标识查询
     */
    @GetMapping("/key/{permKey}")
    public Result<SysPermission> getByPermKey(@PathVariable String permKey) {
        SysPermission permission = sysPermissionService.selectByPermKey(permKey);
        if (permission != null) {
            return Result.success(permission);
        } else {
            return Result.error("权限不存在");
        }
    }

    /**
     * 新增权限
     */
    @PostMapping
    @OperLog(module = "菜单权限", operType = 1, description = "新增权限")
    public Result<String> insert(@RequestBody SysPermission sysPermission) {
        int result = sysPermissionService.insert(sysPermission);
        if (result > 0) {
            return Result.success("新增成功");
        } else {
            return Result.error("新增失败");
        }
    }

    /**
     * 更新权限
     */
    @PutMapping
    @OperLog(module = "菜单权限", operType = 2, description = "更新权限")
    public Result<String> update(@RequestBody SysPermission sysPermission) {
        int result = sysPermissionService.update(sysPermission);
        if (result > 0) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "菜单权限", operType = 3, description = "删除权限")
    public Result<String> delete(@PathVariable Long id) {
        int result = sysPermissionService.deleteById(id);
        if (result > 0) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
}
