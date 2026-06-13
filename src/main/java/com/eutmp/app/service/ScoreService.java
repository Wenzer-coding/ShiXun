package com.eutmp.app.service;

import com.eutmp.app.bean.Score;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 成绩信息Service接口
 */
public interface ScoreService {
    
    /**
     * 分页查询成绩
     */
    PageResult<Score> selectByPage(Integer pageNum, Integer pageSize, Score score);
    
    /**
     * 根据ID查询成绩
     */
    Score selectById(Long id);
    
    /**
     * 查询所有成绩
     */
    List<Score> selectAll();
    
    /**
     * 新增成绩
     */
    int insert(Score score);
    
    /**
     * 修改成绩
     */
    int update(Score score);
    
    /**
     * 删除成绩
     */
    int deleteById(Long id);
    
    /**
     * 批量删除成绩
     */
    int deleteByIds(Long[] ids);
    
    /**
     * 查询学生某课程的成绩
     */
    Score selectByStudentAndCourse(Long studentId, Long courseId);
}
