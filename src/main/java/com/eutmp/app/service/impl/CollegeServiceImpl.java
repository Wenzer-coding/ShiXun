package com.eutmp.app.service.impl;

import com.eutmp.app.bean.College;
import com.eutmp.app.mapper.CollegeMapper;
import com.eutmp.app.service.CollegeService;
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
public class CollegeServiceImpl implements CollegeService {
    
    @Autowired
    private CollegeMapper collegeMapper;
    
    @Override
    public PageResult<College> selectByPage(Integer pageNum, Integer pageSize, College college) {
        PageHelper.startPage(pageNum, pageSize);
        List<College> list = collegeMapper.selectAll(college);
        PageInfo<College> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }
    
    @Override
    public College selectById(Long id) {
        return collegeMapper.selectById(id);
    }
    
    @Override
    public List<College> selectAll(College college) {
        return collegeMapper.selectAll(college);
    }
    
    @Override
    public int insert(College college) {
        college.setStatus(college.getStatus() != null ? college.getStatus() : 1);
        college.setCreateTime(LocalDateTime.now());
        college.setUpdateTime(LocalDateTime.now());
        return collegeMapper.insert(college);
    }
    
    @Override
    public int update(College college) {
        college.setUpdateTime(LocalDateTime.now());
        return collegeMapper.update(college);
    }
    
    @Override
    public int deleteById(Long id) {
        return collegeMapper.deleteById(id);
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        return collegeMapper.deleteByIds(ids);
    }
    
    @Override
    public boolean checkCollegeCodeUnique(College college) {
        College exist = collegeMapper.checkCollegeCodeUnique(college);
        return exist == null || exist.getId().equals(college.getId());
    }
}
