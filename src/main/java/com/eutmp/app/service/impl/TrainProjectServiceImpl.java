package com.eutmp.app.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.eutmp.app.bean.TrainProject;
import com.eutmp.app.mapper.TrainProjectMapper;
import com.eutmp.app.service.TrainProjectService;
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
public class TrainProjectServiceImpl implements TrainProjectService {
    
    @Autowired
    private TrainProjectMapper trainProjectMapper;
    
    @Override
    public PageResult<TrainProject> selectByPage(Integer pageNum, Integer pageSize, TrainProject trainProject) {
        PageHelper.startPage(pageNum, pageSize);
        List<TrainProject> list = trainProjectMapper.selectAll(trainProject);
        PageInfo<TrainProject> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public TrainProject selectById(Long id) {
        return trainProjectMapper.selectById(id);
    }
    
    @Override
    public List<TrainProject> selectAll(TrainProject trainProject) {
        return trainProjectMapper.selectAll(trainProject);
    }
    
    @Override
    public int insert(TrainProject trainProject) {
        trainProject.setStatus(trainProject.getStatus() != null ? trainProject.getStatus() : 1);
        // 设置创建人
        try {
            Long userId = Long.parseLong(String.valueOf(StpUtil.getLoginId()));
            trainProject.setCreateUser(userId);
        } catch (Exception e) {
            log.warn("获取当前登录用户失败，使用默认创建人", e);
        }
        trainProject.setCreateTime(LocalDateTime.now());
        trainProject.setUpdateTime(LocalDateTime.now());
        return trainProjectMapper.insert(trainProject);
    }
    
    @Override
    public int update(TrainProject trainProject) {
        trainProject.setUpdateTime(LocalDateTime.now());
        return trainProjectMapper.update(trainProject);
    }
    
    @Override
    public int deleteById(Long id) {
        return trainProjectMapper.deleteById(id);
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        return trainProjectMapper.deleteByIds(ids);
    }
    
    @Override
    public boolean checkProjectCodeUnique(TrainProject trainProject) {
        TrainProject exist = trainProjectMapper.checkProjectCodeUnique(trainProject);
        return exist == null || exist.getId().equals(trainProject.getId());
    }
}
