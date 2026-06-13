package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.SysPermission;
import com.eutmp.app.bean.SysRolePermission;
import com.eutmp.app.service.SysPermissionService;
import com.eutmp.app.service.SysRolePermissionService;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色权限分配控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/role/permission")
public class SysRolePermissionController {
    
    @Autowired
    private SysPermissionService sysPermissionService;
    
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    
    /**
     * 获取角色的权限列表
     */
    @GetMapping("/{roleId}")
    public Result<List<Long>> getRolePermissions(@PathVariable Long roleId) {
        try {
            log.info("查询角色权限，角色ID: {}", roleId);
            List<SysRolePermission> rolePermissions = sysRolePermissionService.selectByRoleId(roleId);
            
            // 防止空指针异常
            if (rolePermissions == null) {
                log.warn("角色权限列表为null，角色ID: {}，返回空列表", roleId);
                return Result.success(new ArrayList<>());
            }
            
            List<Long> permIds = rolePermissions.stream()
                    .map(SysRolePermission::getPermId)
                    .collect(Collectors.toList());
            
            log.info("角色 {} 已分配权限数量: {}", roleId, permIds.size());
            return Result.success(permIds);
        } catch (Exception e) {
            log.error("获取角色权限失败", e);
            return Result.error("获取角色权限失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有菜单权限树（用于分配）
     */
    @GetMapping("/tree")
    public Result<List<SysPermission>> getPermissionTree() {
        try {
            log.info("=== 开始获取权限树 ===");
            List<SysPermission> allPermissions = sysPermissionService.selectAll();
            log.info("查询到所有权限数量: {}", allPermissions != null ? allPermissions.size() : 0);
            
            // 只返回菜单和按钮类型，不包括接口
            List<SysPermission> menuAndButtons = allPermissions.stream()
                    .filter(p -> {
                        boolean match = p.getPermType() == 1 || p.getPermType() == 2;
                        if (!match) {
                            log.debug("过滤掉权限: {} (类型={})", p.getPermName(), p.getPermType());
                        }
                        return match;
                    })
                    .sorted((p1, p2) -> {
                        int parentIdCompare = p1.getParentId().compareTo(p2.getParentId());
                        if (parentIdCompare != 0) {
                            return parentIdCompare;
                        }
                        return p1.getSort().compareTo(p2.getSort());
                    })
                    .collect(Collectors.toList());
            
            log.info("返回权限树数量: {}", menuAndButtons.size());
            log.info("权限树数据: {}", menuAndButtons);
            
            return Result.success(menuAndButtons);
        } catch (Exception e) {
            log.error("获取权限树失败", e);
            log.error("异常类型: {}", e.getClass().getName());
            log.error("异常消息: {}", e.getMessage());
            e.printStackTrace();
            return Result.error("获取权限树失败: " + e.getMessage());
        }
    }
    
    /**
     * 为角色分配权限
     */
    @PostMapping("/assign")
    @OperLog(module = "角色管理", operType = 2, description = "分配角色权限")
    public Result<String> assignPermissions(@RequestParam Long roleId, @RequestBody List<Long> permIds) {
        try {
            log.info("=== 开始分配角色权限 ===");
            log.info("角色ID: {}", roleId);
            log.info("权限ID列表: {}", permIds);
            
            // 先删除角色的所有权限
            int deletedCount = sysRolePermissionService.deleteByRoleId(roleId);
            log.info("删除角色 {} 的旧权限数量: {}", roleId, deletedCount);
            
            // 如果有权限ID，批量新增
            if (permIds != null && !permIds.isEmpty()) {
                log.info("准备插入 {} 个权限", permIds.size());
                
                List<SysRolePermission> rolePermissions = permIds.stream()
                        .map(permId -> {
                            SysRolePermission rp = new SysRolePermission();
                            rp.setRoleId(roleId);
                            rp.setPermId(permId);
                            return rp;
                        })
                        .collect(Collectors.toList());
                
                int insertedCount = sysRolePermissionService.batchInsert(rolePermissions);
                log.info("✅ 成功插入 {} 个权限", insertedCount);
            } else {
                log.info("权限ID列表为空，仅删除旧权限");
            }
            
            log.info("=== 角色权限分配完成 ===");
            return Result.success("分配权限成功");
        } catch (Exception e) {
            log.error("❌ 分配权限失败", e);
            log.error("异常类型: {}", e.getClass().getName());
            log.error("异常消息: {}", e.getMessage());
            return Result.error("分配权限失败: " + e.getMessage());
        }
    }
}
