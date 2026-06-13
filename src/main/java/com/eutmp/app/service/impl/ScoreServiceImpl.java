package com.eutmp.app.service.impl;

import com.eutmp.app.bean.Score;
import com.eutmp.app.mapper.ScoreMapper;
import com.eutmp.app.service.ScoreService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 成绩信息Service实现类
 */
@Slf4j
@Service
public class ScoreServiceImpl implements ScoreService {
    
    @Autowired
    private ScoreMapper scoreMapper;
    
    @Override
    public PageResult<Score> selectByPage(Integer pageNum, Integer pageSize, Score score) {
        log.info("分页查询成绩: pageNum={}, pageSize={}, score={}", pageNum, pageSize, score);
        
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 查询数据
        List<Score> list = scoreMapper.selectScoreList(score);
        
        // 包装成PageInfo
        PageInfo<Score> pageInfo = new PageInfo<>(list);
        
        log.info("分页查询结果: total={}, pages={}", pageInfo.getTotal(), pageInfo.getPages());
        
        // 返回统一的分页结果
        return PageResult.of(
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
    }
    
    @Override
    public Score selectById(Long id) {
        return scoreMapper.selectScoreById(id);
    }
    
    @Override
    public List<Score> selectAll() {
        return scoreMapper.selectScoreList(new Score());
    }
    
    @Override
    public int insert(Score score) {
        log.info("新增成绩: studentId={}, courseId={}", score.getStudentId(), score.getCourseId());
        
        // 计算总成绩
        if (score.getExamScore() != null && score.getUsualScore() != null) {
            score.setTotalScore(score.getExamScore() * 0.6 + score.getUsualScore() * 0.4);
        }
        
        // 设置默认值
        score.setCreateTime(LocalDateTime.now());
        score.setUpdateTime(LocalDateTime.now());
        
        // 插入数据库
        int result = scoreMapper.insertScore(score);
        log.info("新增成绩结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int update(Score score) {
        log.info("修改成绩: id={}, studentId={}", score.getId(), score.getStudentId());
        
        // 计算总成绩
        if (score.getExamScore() != null && score.getUsualScore() != null) {
            score.setTotalScore(score.getExamScore() * 0.6 + score.getUsualScore() * 0.4);
        }
        
        // 设置更新时间
        score.setUpdateTime(LocalDateTime.now());
        
        // 更新数据库
        int result = scoreMapper.updateScore(score);
        log.info("修改成绩结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int deleteById(Long id) {
        log.info("删除成绩: id={}", id);
        
        // 删除数据库记录
        int result = scoreMapper.deleteScoreById(id);
        log.info("删除成绩结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        log.info("批量删除成绩: ids={}", (Object) ids);
        
        // 批量删除数据库记录
        int result = scoreMapper.deleteScoreByIds(ids);
        log.info("批量删除成绩结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public Score selectByStudentAndCourse(Long studentId, Long courseId) {
        return scoreMapper.selectScoreByStudentAndCourse(studentId, courseId);
    }
}
