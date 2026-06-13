package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.SysRole;
import com.eutmp.app.service.SysRoleService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色表控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询所有角色（用于下拉选择）
     */
    @GetMapping("/list")
    public Result<List<SysRole>> list() {
        List<SysRole> roleList = sysRoleService.selectAll();
        return Result.success(roleList);
    }

    /**
     * 分页查询角色
     */
    @GetMapping("/page")
    public Result<PageResult<SysRole>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Integer status) {
        PageResult<SysRole> pageResult = sysRoleService.selectByPage(pageNum, pageSize, roleName, status);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询角色
     */
    @GetMapping("/{id}")
    public Result<SysRole> getById(@PathVariable Long id) {
        SysRole role = sysRoleService.selectById(id);
        if (role == null) {
            return Result.error("角色不存在");
        }
        return Result.success(role);
    }

    /**
     * 根据角色编码查询
     */
    @GetMapping("/code/{roleCode}")
    public Result<SysRole> getByRoleCode(@PathVariable String roleCode) {
        SysRole role = sysRoleService.selectByRoleCode(roleCode);
        return Result.success(role);
    }

    /**
     * 新增角色
     */
    @PostMapping
    @OperLog(module = "角色管理", operType = 1, description = "新增角色")
    public Result<String> insert(@RequestBody SysRole sysRole) {
        sysRoleService.insert(sysRole);
        return Result.success("新增角色成功");
    }

    /**
     * 更新角色
     */
    @PutMapping
    @OperLog(module = "角色管理", operType = 2, description = "更新角色")
    public Result<String> update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return Result.success("更新角色成功");
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "角色管理", operType = 3, description = "删除角色")
    public Result<String> delete(@PathVariable Long id) {
        sysRoleService.deleteById(id);
        return Result.success("删除角色成功");
    }

    /**
     * 批量删除角色
     */
    @DeleteMapping("/batch")
    @OperLog(module = "角色管理", operType = 3, description = "批量删除角色")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("请选择要删除的角色");
        }
        sysRoleService.batchDeleteByIds(ids);
        return Result.success("批量删除角色成功");
    }
}
