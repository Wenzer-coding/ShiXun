package com.eutmp.app.service.impl;

import com.eutmp.app.bean.TrainEnroll;
import com.eutmp.app.mapper.TrainEnrollMapper;
import com.eutmp.app.service.TrainEnrollService;
import com.eutmp.app.service.TrainPlanService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TrainEnrollServiceImpl implements TrainEnrollService {
    
    @Autowired
    private TrainEnrollMapper trainEnrollMapper;

    @Autowired
    private TrainPlanService trainPlanService;
    
    @Override
    public PageResult<TrainEnroll> selectByPage(Integer pageNum, Integer pageSize, TrainEnroll trainEnroll) {
        PageHelper.startPage(pageNum, pageSize);
        List<TrainEnroll> list = trainEnrollMapper.selectAll(trainEnroll);
        PageInfo<TrainEnroll> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public TrainEnroll selectById(Long id) {
        return trainEnrollMapper.selectById(id);
    }
    
    @Override
    public List<TrainEnroll> selectAll(TrainEnroll trainEnroll) {
        return trainEnrollMapper.selectAll(trainEnroll);
    }
    
    @Override
    public int insert(TrainEnroll trainEnroll) {
        if (trainEnroll.getEnrollTime() == null) {
            trainEnroll.setEnrollTime(LocalDateTime.now());
        }
        if (trainEnroll.getAuditStatus() == null) {
            trainEnroll.setAuditStatus(1); // 默认待审核
        }
        trainEnroll.setCreateTime(LocalDateTime.now());
        trainEnroll.setUpdateTime(LocalDateTime.now());
        return trainEnrollMapper.insert(trainEnroll);
    }
    
    @Override
    public int update(TrainEnroll trainEnroll) {
        trainEnroll.setUpdateTime(LocalDateTime.now());
        return trainEnrollMapper.update(trainEnroll);
    }
    
    @Override
    public int deleteById(Long id) {
        return trainEnrollMapper.deleteById(id);
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        return trainEnrollMapper.deleteByIds(ids);
    }
    
    @Override
    public boolean checkEnrollUnique(TrainEnroll trainEnroll) {
        TrainEnroll exist = trainEnrollMapper.checkEnrollUnique(trainEnroll.getPlanId(), trainEnroll.getStuId());
        return exist == null || exist.getId().equals(trainEnroll.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long id, Long auditUser, String auditRemark) {
        TrainEnroll enroll = trainEnrollMapper.selectById(id);
        if (enroll == null) {
            throw new RuntimeException("报名记录不存在");
        }
        if (enroll.getAuditStatus() != null && enroll.getAuditStatus() != 1) {
            throw new RuntimeException("该报名已审核，不可重复审核");
        }
        enroll.setAuditStatus(2);
        enroll.setAuditUser(auditUser);
        enroll.setAuditTime(LocalDateTime.now());
        enroll.setAuditRemark(auditRemark);
        enroll.setUpdateTime(LocalDateTime.now());
        trainEnrollMapper.update(enroll);
        // 同步增加开班已报名人数
        trainPlanService.incrementEnrollNum(enroll.getPlanId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(Long id, Long auditUser, String auditRemark) {
        TrainEnroll enroll = trainEnrollMapper.selectById(id);
        if (enroll == null) {
            throw new RuntimeException("报名记录不存在");
        }
        if (enroll.getAuditStatus() != null && enroll.getAuditStatus() != 1) {
            throw new RuntimeException("该报名已审核，不可重复审核");
        }
        enroll.setAuditStatus(3);
        enroll.setAuditUser(auditUser);
        enroll.setAuditTime(LocalDateTime.now());
        enroll.setAuditRemark(auditRemark);
        enroll.setUpdateTime(LocalDateTime.now());
        trainEnrollMapper.update(enroll);
    }
}
