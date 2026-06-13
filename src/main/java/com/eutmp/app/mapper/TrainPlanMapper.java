package com.eutmp.app.mapper;

import com.eutmp.app.bean.TrainPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实训开班Mapper接口
 */
@Mapper
public interface TrainPlanMapper {
    
    TrainPlan selectById(@Param("id") Long id);
    
    List<TrainPlan> selectAll(TrainPlan trainPlan);
    
    int insert(TrainPlan trainPlan);
    
    int update(TrainPlan trainPlan);
    
    int deleteById(@Param("id") Long id);
    
    int deleteByIds(@Param("ids") Long[] ids);

    int incrementEnrollNum(@Param("id") Long id);

    int decrementEnrollNum(@Param("id") Long id);
}
