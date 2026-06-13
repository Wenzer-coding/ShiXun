package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.SysOperLog;
import com.eutmp.app.service.SysOperLogService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统操作日志表控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/operlog")
public class SysOperLogController {

    @Autowired
    private SysOperLogService sysOperLogService;

    /**
     * 分页查询日志
     */
    @GetMapping("/page")
    public Result<PageResult<SysOperLog>> getPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String operModule,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String createTime) {
        PageResult<SysOperLog> pageResult = sysOperLogService.selectByPage(pageNum, pageSize, operModule, username, createTime);
        return Result.success(pageResult);
    }

    /**
     * 删除日志
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "操作日志", operType = 3, description = "删除日志")
    public Result<String> delete(@PathVariable Long id) {
        sysOperLogService.deleteById(id);
        return Result.success("删除成功", null);
    }

    /**
     * 批量删除日志
     */
    @DeleteMapping("/batch")
    @OperLog(module = "操作日志", operType = 3, description = "批量删除日志")
    public Result<String> deleteBatch(@RequestParam Long[] ids) {
        if (ids == null || ids.length == 0) {
            return Result.error("请选择要删除的日志");
        }
        sysOperLogService.deleteBatch(ids);
        return Result.success("批量删除成功", null);
    }
}
