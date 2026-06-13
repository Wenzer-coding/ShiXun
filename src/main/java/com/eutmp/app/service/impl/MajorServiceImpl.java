package com.eutmp.app.service.impl;

import com.eutmp.app.bean.Major;
import com.eutmp.app.mapper.MajorMapper;
import com.eutmp.app.service.MajorService;
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
public class MajorServiceImpl implements MajorService {
    
    @Autowired
    private MajorMapper majorMapper;
    
    @Override
    public PageResult<Major> selectByPage(Integer pageNum, Integer pageSize, Major major) {
        PageHelper.startPage(pageNum, pageSize);
        List<Major> list = majorMapper.selectAll(major);
        PageInfo<Major> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public Major selectById(Long id) {
        return majorMapper.selectById(id);
    }
    
    @Override
    public List<Major> selectAll(Major major) {
        return majorMapper.selectAll(major);
    }
    
    @Override
    public int insert(Major major) {
        major.setStatus(major.getStatus() != null ? major.getStatus() : 1);
        major.setCreateTime(LocalDateTime.now());
        major.setUpdateTime(LocalDateTime.now());
        return majorMapper.insert(major);
    }
    
    @Override
    public int update(Major major) {
        major.setUpdateTime(LocalDateTime.now());
        return majorMapper.update(major);
    }
    
    @Override
    public int deleteById(Long id) {
        return majorMapper.deleteById(id);
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        return majorMapper.deleteByIds(ids);
    }
    
    @Override
    public boolean checkMajorCodeUnique(Major major) {
        Major exist = majorMapper.checkMajorCodeUnique(major);
        return exist == null || exist.getId().equals(major.getId());
    }
}
