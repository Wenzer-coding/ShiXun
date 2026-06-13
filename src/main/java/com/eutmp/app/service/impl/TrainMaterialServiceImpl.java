package com.eutmp.app.service.impl;

import com.eutmp.app.bean.TrainMaterial;
import com.eutmp.app.mapper.TrainMaterialMapper;
import com.eutmp.app.service.TrainMaterialService;
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
public class TrainMaterialServiceImpl implements TrainMaterialService {
    
    @Autowired
    private TrainMaterialMapper trainMaterialMapper;
    
    @Override
    public PageResult<TrainMaterial> selectByPage(Integer pageNum, Integer pageSize, TrainMaterial trainMaterial) {
        PageHelper.startPage(pageNum, pageSize);
        List<TrainMaterial> list = trainMaterialMapper.selectAll(trainMaterial);
        PageInfo<TrainMaterial> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public TrainMaterial selectById(Long id) {
        return trainMaterialMapper.selectById(id);
    }
    
    @Override
    public List<TrainMaterial> selectAll(TrainMaterial trainMaterial) {
        return trainMaterialMapper.selectAll(trainMaterial);
    }
    
    @Override
    public int insert(TrainMaterial trainMaterial) {
        trainMaterial.setStatus(trainMaterial.getStatus() != null ? trainMaterial.getStatus() : 1);
        trainMaterial.setCreateTime(LocalDateTime.now());
        trainMaterial.setUpdateTime(LocalDateTime.now());
        return trainMaterialMapper.insert(trainMaterial);
    }
    
    @Override
    public int update(TrainMaterial trainMaterial) {
        trainMaterial.setUpdateTime(LocalDateTime.now());
        return trainMaterialMapper.update(trainMaterial);
    }
    
    @Override
    public int deleteById(Long id) {
        return trainMaterialMapper.deleteById(id);
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        return trainMaterialMapper.deleteByIds(ids);
    }
}
