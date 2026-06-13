package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.TrainScore;
import com.eutmp.app.service.TrainScoreService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/trainScore")
public class TrainScoreController {
    
    @Autowired
    private TrainScoreService trainScoreService;
    
    @GetMapping("/list")
    public Result<PageResult<TrainScore>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long planId,
            @RequestParam(required = false) String grade) {
        try {
            TrainScore trainScore = new TrainScore();
            trainScore.setPlanId(planId);
            trainScore.setGrade(grade);
            return Result.success(trainScoreService.selectByPage(pageNum, pageSize, trainScore));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<TrainScore> getById(@PathVariable Long id) {
        try {
            return Result.success(trainScoreService.selectById(id));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    @OperLog(module = "实训成绩", operType = 1)
    public Result<String> insert(@RequestBody TrainScore trainScore) {
        try {
            if (!trainScoreService.checkScoreUnique(trainScore)) {
                return Result.error("该学生成绩已存在");
            }
            trainScoreService.insert(trainScore);
            return Result.success("新增成功");
        } catch (Exception e) {
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    @PutMapping
    @OperLog(module = "实训成绩", operType = 2)
    public Result<String> update(@RequestBody TrainScore trainScore) {
        try {
            trainScoreService.update(trainScore);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @OperLog(module = "实训成绩", operType = 3)
    public Result<String> delete(@PathVariable Long id) {
        try {
            trainScoreService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/batch")
    @OperLog(module = "实训成绩", operType = 3)
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            trainScoreService.deleteByIds(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
