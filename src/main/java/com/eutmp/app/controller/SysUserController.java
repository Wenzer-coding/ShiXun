package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.SysUser;
import com.eutmp.app.bean.SysUserRole;
import com.eutmp.app.service.SysUserRoleService;
import com.eutmp.app.service.SysUserService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 分页查询用户
     */
    @GetMapping("/page")
    public Result<PageResult<SysUser>> getPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickName,
            @RequestParam(required = false) Long deptId) {
        PageResult<SysUser> pageResult = sysUserService.selectByPage(pageNum, pageSize, username, nickName, deptId);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询用户（包含角色）
     */
    @GetMapping("/{id}")
    public Result<SysUser> getById(@PathVariable Long id) {
        SysUser user = sysUserService.selectById(id);
        if (user != null) {
            // 查询用户的角色
            List<SysUserRole> userRoles = sysUserRoleService.selectByUserId(id);
            List<Long> roleIds = new ArrayList<>();
            if (userRoles != null && !userRoles.isEmpty()) {
                for (SysUserRole ur : userRoles) {
                    roleIds.add(ur.getRoleId());
                }
            }
            user.setRoleIds(roleIds);
        }
        return Result.success(user);
    }

    /**
     * 新增用户（包含角色关联）
     */
    @PostMapping
    @OperLog(module = "用户管理", operType = 1, description = "新增用户")
    public Result<String> insert(@RequestBody SysUser sysUser) {
        // 1. 新增用户
        sysUserService.insert(sysUser);

        // 2. 保存用户角色关联
        if (sysUser.getRoleIds() != null && !sysUser.getRoleIds().isEmpty()) {
            List<SysUserRole> userRoles = new ArrayList<>();
            for (Long roleId : sysUser.getRoleIds()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getId());
                userRole.setRoleId(roleId);
                userRoles.add(userRole);
            }
            sysUserRoleService.batchInsert(userRoles);
        }

        return Result.success("新增用户成功");
    }

    /**
     * 更新用户（包含角色关联）
     */
    @PutMapping
    @OperLog(module = "用户管理", operType = 2, description = "修改用户")
    public Result<String> update(@RequestBody SysUser sysUser) {
        // 1. 更新用户信息
        sysUserService.update(sysUser);

        // 2. 删除旧的角色关联
        sysUserRoleService.deleteByUserId(sysUser.getId());

        // 3. 保存新的角色关联
        if (sysUser.getRoleIds() != null && !sysUser.getRoleIds().isEmpty()) {
            List<SysUserRole> userRoles = new ArrayList<>();
            for (Long roleId : sysUser.getRoleIds()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getId());
                userRole.setRoleId(roleId);
                userRoles.add(userRole);
            }
            sysUserRoleService.batchInsert(userRoles);
        }

        return Result.success("修改用户成功");
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "用户管理", operType = 3, description = "删除用户")
    public Result<String> delete(@PathVariable Long id) {
        sysUserService.deleteById(id);
        return Result.success("删除用户成功");
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batch")
    @OperLog(module = "用户管理", operType = 3, description = "批量删除用户")
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        if (ids == null || ids.length == 0) {
            return Result.error("请选择要删除的用户");
        }
        for (Long id : ids) {
            sysUserService.deleteById(id);
        }
        return Result.success("批量删除用户成功");
    }

    /**
     * 批量删除用户的角色
     */
    @DeleteMapping("/{userId}/roles")
    @OperLog(module = "用户管理", operType = 3, description = "批量删除用户角色")
    public Result<String> batchDeleteUserRoles(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Result.error("请选择要删除的角色");
        }
        sysUserRoleService.batchDeleteByUserIdAndRoleIds(userId, roleIds);
        return Result.success("批量删除角色成功");
    }
}
