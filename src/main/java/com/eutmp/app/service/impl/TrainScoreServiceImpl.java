package com.eutmp.app.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.eutmp.app.bean.TrainScore;
import com.eutmp.app.mapper.TrainScoreMapper;
import com.eutmp.app.service.TrainScoreService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TrainScoreServiceImpl implements TrainScoreService {
    
    @Autowired
    private TrainScoreMapper trainScoreMapper;
    
    @Override
    public PageResult<TrainScore> selectByPage(Integer pageNum, Integer pageSize, TrainScore trainScore) {
        PageHelper.startPage(pageNum, pageSize);
        List<TrainScore> list = trainScoreMapper.selectAll(trainScore);
        PageInfo<TrainScore> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public TrainScore selectById(Long id) {
        return trainScoreMapper.selectById(id);
    }
    
    @Override
    public List<TrainScore> selectAll(TrainScore trainScore) {
        return trainScoreMapper.selectAll(trainScore);
    }
    
    @Override
    public int insert(TrainScore trainScore) {
        // 设置评分教师(当前登录用户)
        try {
            Long userId = Long.parseLong(String.valueOf(StpUtil.getLoginId()));
            trainScore.setScoreTeacher(userId);
        } catch (Exception e) {
            log.warn("获取当前登录用户失败", e);
        }
        // 计算总分
        if (trainScore.getUsualScore() != null && trainScore.getReportScore() != null && trainScore.getDefenseScore() != null) {
            BigDecimal total = trainScore.getUsualScore()
                .add(trainScore.getReportScore())
                .add(trainScore.getDefenseScore());
            trainScore.setTotalScore(total);
            
            // 自动计算等级
            if (total.compareTo(new BigDecimal("90")) >= 0) {
                trainScore.setGrade("优秀");
            } else if (total.compareTo(new BigDecimal("80")) >= 0) {
                trainScore.setGrade("良好");
            } else if (total.compareTo(new BigDecimal("70")) >= 0) {
                trainScore.setGrade("中等");
            } else if (total.compareTo(new BigDecimal("60")) >= 0) {
                trainScore.setGrade("及格");
            } else {
                trainScore.setGrade("不及格");
            }
        }
        
        trainScore.setScoreTime(LocalDateTime.now());
        trainScore.setCreateTime(LocalDateTime.now());
        trainScore.setUpdateTime(LocalDateTime.now());
        return trainScoreMapper.insert(trainScore);
    }
    
    @Override
    public int update(TrainScore trainScore) {
        // 重新计算总分
        if (trainScore.getUsualScore() != null && trainScore.getReportScore() != null && trainScore.getDefenseScore() != null) {
            BigDecimal total = trainScore.getUsualScore()
                .add(trainScore.getReportScore())
                .add(trainScore.getDefenseScore());
            trainScore.setTotalScore(total);
            
            // 重新计算等级
            if (total.compareTo(new BigDecimal("90")) >= 0) {
                trainScore.setGrade("优秀");
            } else if (total.compareTo(new BigDecimal("80")) >= 0) {
                trainScore.setGrade("良好");
            } else if (total.compareTo(new BigDecimal("70")) >= 0) {
                trainScore.setGrade("中等");
            } else if (total.compareTo(new BigDecimal("60")) >= 0) {
                trainScore.setGrade("及格");
            } else {
                trainScore.setGrade("不及格");
            }
        }
        
        trainScore.setScoreTime(LocalDateTime.now());
        trainScore.setUpdateTime(LocalDateTime.now());
        return trainScoreMapper.update(trainScore);
    }
    
    @Override
    public int deleteById(Long id) {
        return trainScoreMapper.deleteById(id);
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        return trainScoreMapper.deleteByIds(ids);
    }
    
    @Override
    public boolean checkScoreUnique(TrainScore trainScore) {
        TrainScore exist = trainScoreMapper.checkScoreUnique(trainScore.getPlanId(), trainScore.getStuId());
        return exist == null || exist.getId().equals(trainScore.getId());
    }
}
