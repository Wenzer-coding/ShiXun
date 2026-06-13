package com.eutmp.app.service;

import com.eutmp.app.bean.Major;
import com.eutmp.app.utils.PageResult;

import java.util.List;

/**
 * 专业信息Service接口
 */
public interface MajorService {
    
    PageResult<Major> selectByPage(Integer pageNum, Integer pageSize, Major major);
    
    Major selectById(Long id);
    
    List<Major> selectAll(Major major);
    
    int insert(Major major);
    
    int update(Major major);
    
    int deleteById(Long id);
    
    int deleteByIds(Long[] ids);
    
    boolean checkMajorCodeUnique(Major major);
}
