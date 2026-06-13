package com.eutmp.app.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.TrainEnroll;
import com.eutmp.app.service.TrainEnrollService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/enroll")
public class TrainEnrollController {
    
    @Autowired
    private TrainEnrollService trainEnrollService;
    
    @GetMapping("/list")
    public Result<PageResult<TrainEnroll>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long planId,
            @RequestParam(required = false) Integer auditStatus) {
        try {
            TrainEnroll enroll = new TrainEnroll();
            enroll.setPlanId(planId);
            enroll.setAuditStatus(auditStatus);
            return Result.success(trainEnrollService.selectByPage(pageNum, pageSize, enroll));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<TrainEnroll> getById(@PathVariable Long id) {
        try {
            return Result.success(trainEnrollService.selectById(id));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    @OperLog(module = "报名管理", operType = 1)
    public Result<String> insert(@RequestBody TrainEnroll trainEnroll) {
        try {
            if (!trainEnrollService.checkEnrollUnique(trainEnroll)) {
                return Result.error("该学生已报名此开班");
            }
            trainEnrollService.insert(trainEnroll);
            return Result.success("报名成功");
        } catch (Exception e) {
            return Result.error("报名失败: " + e.getMessage());
        }
    }
    
    @PutMapping
    @OperLog(module = "报名管理", operType = 2)
    public Result<String> update(@RequestBody TrainEnroll trainEnroll) {
        try {
            trainEnrollService.update(trainEnroll);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @OperLog(module = "报名管理", operType = 3)
    public Result<String> delete(@PathVariable Long id) {
        try {
            trainEnrollService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/batch")
    @OperLog(module = "报名管理", operType = 3)
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            trainEnrollService.deleteByIds(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }

    /**
     * 审核通过报名
     */
    @PutMapping("/audit/{id}")
    @OperLog(module = "报名管理", operType = 2, description = "审核通过报名")
    public Result<String> audit(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Long auditUser = Long.parseLong(String.valueOf(StpUtil.getLoginId()));
            String auditRemark = body.getOrDefault("auditRemark", "");
            trainEnrollService.audit(id, auditUser, auditRemark);
            return Result.success("审核通过");
        } catch (Exception e) {
            return Result.error("审核失败: " + e.getMessage());
        }
    }

    /**
     * 驳回报名
     */
    @PutMapping("/reject/{id}")
    @OperLog(module = "报名管理", operType = 2, description = "驳回报名")
    public Result<String> reject(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Long auditUser = Long.parseLong(String.valueOf(StpUtil.getLoginId()));
            String auditRemark = body.getOrDefault("auditRemark", "");
            trainEnrollService.reject(id, auditUser, auditRemark);
            return Result.success("已驳回");
        } catch (Exception e) {
            return Result.error("驳回失败: " + e.getMessage());
        }
    }
}
