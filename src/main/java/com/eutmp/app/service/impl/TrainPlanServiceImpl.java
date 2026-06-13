package com.eutmp.app.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.eutmp.app.bean.TrainPlan;
import com.eutmp.app.mapper.TrainPlanMapper;
import com.eutmp.app.service.TrainPlanService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TrainPlanServiceImpl implements TrainPlanService {
    
    @Autowired
    private TrainPlanMapper trainPlanMapper;
    
    @Override
    public PageResult<TrainPlan> selectByPage(Integer pageNum, Integer pageSize, TrainPlan trainPlan) {
        PageHelper.startPage(pageNum, pageSize);
        List<TrainPlan> list = trainPlanMapper.selectAll(trainPlan);
        PageInfo<TrainPlan> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public TrainPlan selectById(Long id) {
        return trainPlanMapper.selectById(id);
    }
    
    @Override
    public List<TrainPlan> selectAll(TrainPlan trainPlan) {
        return trainPlanMapper.selectAll(trainPlan);
    }
    
    @Override
    public int insert(TrainPlan trainPlan) {
        // 设置默认主讲教师(当前登录用户)
        if (trainPlan.getTeacherId() == null) {
            try {
                Long userId = Long.parseLong(String.valueOf(StpUtil.getLoginId()));
                trainPlan.setTeacherId(userId);
            } catch (Exception e) {
                log.warn("获取当前登录用户失败", e);
            }
        }
        trainPlan.setEnrollNum(trainPlan.getEnrollNum() != null ? trainPlan.getEnrollNum() : 0);
        trainPlan.setCreateTime(LocalDateTime.now());
        trainPlan.setUpdateTime(LocalDateTime.now());
        return trainPlanMapper.insert(trainPlan);
    }
    
    @Override
    public int update(TrainPlan trainPlan) {
        trainPlan.setUpdateTime(LocalDateTime.now());
        return trainPlanMapper.update(trainPlan);
    }
    
    @Override
    public int deleteById(Long id) {
        return trainPlanMapper.deleteById(id);
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        return trainPlanMapper.deleteByIds(ids);
    }

    @Override
    public int incrementEnrollNum(Long id) {
        return trainPlanMapper.incrementEnrollNum(id);
    }

    @Override
    public int decrementEnrollNum(Long id) {
        return trainPlanMapper.decrementEnrollNum(id);
    }
}
