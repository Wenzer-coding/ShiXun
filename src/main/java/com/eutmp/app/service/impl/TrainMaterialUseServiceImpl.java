package com.eutmp.app.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.eutmp.app.bean.TrainMaterialUse;
import com.eutmp.app.mapper.TrainMaterialUseMapper;
import com.eutmp.app.service.TrainMaterialUseService;
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
public class TrainMaterialUseServiceImpl implements TrainMaterialUseService {
    
    @Autowired
    private TrainMaterialUseMapper trainMaterialUseMapper;
    
    @Override
    public PageResult<TrainMaterialUse> selectByPage(Integer pageNum, Integer pageSize, TrainMaterialUse trainMaterialUse) {
        PageHelper.startPage(pageNum, pageSize);
        List<TrainMaterialUse> list = trainMaterialUseMapper.selectAll(trainMaterialUse);
        PageInfo<TrainMaterialUse> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public TrainMaterialUse selectById(Long id) {
        return trainMaterialUseMapper.selectById(id);
    }
    
    @Override
    public List<TrainMaterialUse> selectAll(TrainMaterialUse trainMaterialUse) {
        return trainMaterialUseMapper.selectAll(trainMaterialUse);
    }
    
    @Override
    public int insert(TrainMaterialUse trainMaterialUse) {
        // 设置领用人(当前登录用户)
        try {
            Long userId = Long.parseLong(String.valueOf(StpUtil.getLoginId()));
            trainMaterialUse.setUseUser(userId);
        } catch (Exception e) {
            log.warn("获取当前登录用户失败", e);
        }
        trainMaterialUse.setCreateTime(LocalDateTime.now());
        return trainMaterialUseMapper.insert(trainMaterialUse);
    }
    
    @Override
    public int update(TrainMaterialUse trainMaterialUse) {
        return trainMaterialUseMapper.update(trainMaterialUse);
    }
    
    @Override
    public int deleteById(Long id) {
        return trainMaterialUseMapper.deleteById(id);
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        return trainMaterialUseMapper.deleteByIds(ids);
    }
}
