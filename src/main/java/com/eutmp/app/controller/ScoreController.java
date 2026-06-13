package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.Score;
import com.eutmp.app.service.ScoreService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 成绩管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/score")
public class ScoreController {
    
    @Autowired
    private ScoreService scoreService;
    
    /**
     * 分页查询成绩
     */
    @GetMapping("/page")
    public Result<PageResult<Score>> getPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) Integer examType) {
        try {
            Score score = new Score();
            score.setStudentId(studentId);
            score.setCourseId(courseId);
            score.setSemester(semester);
            score.setExamType(examType);
            
            PageResult<Score> pageResult = scoreService.selectByPage(pageNum, pageSize, score);
            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("分页查询成绩失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询成绩
     */
    @GetMapping("/{id}")
    public Result<Score> getById(@PathVariable Long id) {
        try {
            Score score = scoreService.selectById(id);
            return Result.success(score);
        } catch (Exception e) {
            log.error("查询成绩失败", e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 新增成绩
     */
    @PostMapping
    @OperLog(module = "成绩管理", operType = 1, description = "新增成绩")
    public Result<String> insert(@RequestBody Score score) {
        try {
            scoreService.insert(score);
            return Result.success("新增成绩成功");
        } catch (Exception e) {
            log.error("新增成绩失败", e);
            return Result.error("新增失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改成绩
     */
    @PutMapping
    @OperLog(module = "成绩管理", operType = 2, description = "修改成绩")
    public Result<String> update(@RequestBody Score score) {
        try {
            scoreService.update(score);
            return Result.success("修改成绩成功");
        } catch (Exception e) {
            log.error("修改成绩失败", e);
            return Result.error("修改失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除成绩
     */
    @DeleteMapping("/{id}")
    @OperLog(module = "成绩管理", operType = 3, description = "删除成绩")
    public Result<String> delete(@PathVariable Long id) {
        try {
            scoreService.deleteById(id);
            return Result.success("删除成绩成功");
        } catch (Exception e) {
            log.error("删除成绩失败", e);
            return Result.error("删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量删除成绩
     */
    @DeleteMapping("/batch")
    @OperLog(module = "成绩管理", operType = 3, description = "批量删除成绩")
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            if (ids == null || ids.length == 0) {
                return Result.error("请选择要删除的成绩");
            }
            scoreService.deleteByIds(ids);
            return Result.success("批量删除成绩成功");
        } catch (Exception e) {
            log.error("批量删除成绩失败", e);
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }
}
