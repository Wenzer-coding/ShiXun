package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.TrainMaterial;
import com.eutmp.app.service.TrainMaterialService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/material")
public class TrainMaterialController {
    
    @Autowired
    private TrainMaterialService trainMaterialService;
    
    /**
     * 查询所有耗材(用于下拉框)
     */
    @GetMapping("/all")
    public Result<List<TrainMaterial>> getAll() {
        try {
            return Result.success(trainMaterialService.selectAll(new TrainMaterial()));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<PageResult<TrainMaterial>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String materialName,
            @RequestParam(required = false) Integer status) {
        try {
            TrainMaterial material = new TrainMaterial();
            material.setMaterialName(materialName);
            material.setStatus(status);
            return Result.success(trainMaterialService.selectByPage(pageNum, pageSize, material));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<TrainMaterial> getById(@PathVariable Long id) {
        try {
            return Result.success(trainMaterialService.selectById(id));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    @OperLog(module = "耗材管理", operType = 1)
    public Result<String> insert(@RequestBody TrainMaterial trainMaterial) {
        try {
            trainMaterialService.insert(trainMaterial);
            return Result.success("新增成功");
        } catch (Exception e) {
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    @PutMapping
    @OperLog(module = "耗材管理", operType = 2)
    public Result<String> update(@RequestBody TrainMaterial trainMaterial) {
        try {
            trainMaterialService.update(trainMaterial);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @OperLog(module = "耗材管理", operType = 3)
    public Result<String> delete(@PathVariable Long id) {
        try {
            trainMaterialService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/batch")
    @OperLog(module = "耗材管理", operType = 3)
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            trainMaterialService.deleteByIds(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
