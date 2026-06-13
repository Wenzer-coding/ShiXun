package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.TrainPlan;
import com.eutmp.app.service.TrainPlanService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/plan")
public class TrainPlanController {
    
    @Autowired
    private TrainPlanService trainPlanService;
    
    /**
     * 查询所有培训计划(用于下拉框)
     */
    @GetMapping("/all")
    public Result<List<TrainPlan>> getAll() {
        try {
            return Result.success(trainPlanService.selectAll(new TrainPlan()));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<PageResult<TrainPlan>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String planName,
            @RequestParam(required = false) String termYear,
            @RequestParam(required = false) Integer planStatus) {
        try {
            TrainPlan plan = new TrainPlan();
            plan.setPlanName(planName);
            plan.setTermYear(termYear);
            plan.setPlanStatus(planStatus);
            return Result.success(trainPlanService.selectByPage(pageNum, pageSize, plan));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<TrainPlan> getById(@PathVariable Long id) {
        try {
            return Result.success(trainPlanService.selectById(id));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    @OperLog(module = "实训开班", operType = 1)
    public Result<String> insert(@RequestBody TrainPlan trainPlan) {
        try {
            trainPlanService.insert(trainPlan);
            return Result.success("新增成功");
        } catch (Exception e) {
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    @PutMapping
    @OperLog(module = "实训开班", operType = 2)
    public Result<String> update(@RequestBody TrainPlan trainPlan) {
        try {
            trainPlanService.update(trainPlan);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @OperLog(module = "实训开班", operType = 3)
    public Result<String> delete(@PathVariable Long id) {
        try {
            trainPlanService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/batch")
    @OperLog(module = "实训开班", operType = 3)
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            trainPlanService.deleteByIds(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
