package com.eutmp.app.mapper;

import com.eutmp.app.bean.TrainProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实训项目Mapper接口
 */
@Mapper
public interface TrainProjectMapper {
    
    TrainProject selectById(@Param("id") Long id);
    
    List<TrainProject> selectAll(TrainProject trainProject);
    
    int insert(TrainProject trainProject);
    
    int update(TrainProject trainProject);
    
    int deleteById(@Param("id") Long id);
    
    int deleteByIds(@Param("ids") Long[] ids);
    
    TrainProject checkProjectCodeUnique(TrainProject trainProject);
}
