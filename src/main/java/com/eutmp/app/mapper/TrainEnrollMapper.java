package com.eutmp.app.mapper;

import com.eutmp.app.bean.TrainEnroll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实训报名Mapper接口
 */
@Mapper
public interface TrainEnrollMapper {

    TrainEnroll selectById(@Param("id") Long id);

    List<TrainEnroll> selectAll(TrainEnroll trainEnroll);

    int insert(TrainEnroll trainEnroll);

    int update(TrainEnroll trainEnroll);

    int deleteById(@Param("id") Long id);

    int deleteByIds(@Param("ids") Long[] ids);

    TrainEnroll checkEnrollUnique(@Param("planId") Long planId, @Param("stuId") Long stuId);

    TrainEnroll selectByPlanIdAndStuId(@Param("planId") Long planId, @Param("stuId") Long stuId);
}
