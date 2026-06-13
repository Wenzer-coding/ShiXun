package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.TrainMaterialUse;
import com.eutmp.app.service.TrainMaterialUseService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/materialUse")
public class TrainMaterialUseController {
    
    @Autowired
    private TrainMaterialUseService trainMaterialUseService;
    
    @GetMapping("/list")
    public Result<PageResult<TrainMaterialUse>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long planId,
            @RequestParam(required = false) Long materialId) {
        try {
            TrainMaterialUse materialUse = new TrainMaterialUse();
            materialUse.setPlanId(planId);
            materialUse.setMaterialId(materialId);
            return Result.success(trainMaterialUseService.selectByPage(pageNum, pageSize, materialUse));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<TrainMaterialUse> getById(@PathVariable Long id) {
        try {
            return Result.success(trainMaterialUseService.selectById(id));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    @OperLog(module = "耗材领用", operType = 1)
    public Result<String> insert(@RequestBody TrainMaterialUse trainMaterialUse) {
        try {
            trainMaterialUseService.insert(trainMaterialUse);
            return Result.success("新增成功");
        } catch (Exception e) {
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    @PutMapping
    @OperLog(module = "耗材领用", operType = 2)
    public Result<String> update(@RequestBody TrainMaterialUse trainMaterialUse) {
        try {
            trainMaterialUseService.update(trainMaterialUse);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @OperLog(module = "耗材领用", operType = 3)
    public Result<String> delete(@PathVariable Long id) {
        try {
            trainMaterialUseService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/batch")
    @OperLog(module = "耗材领用", operType = 3)
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            trainMaterialUseService.deleteByIds(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
