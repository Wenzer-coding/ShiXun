package com.eutmp.app.mapper;

import com.eutmp.app.bean.TrainScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实训成绩Mapper接口
 */
@Mapper
public interface TrainScoreMapper {
    
    TrainScore selectById(@Param("id") Long id);
    
    List<TrainScore> selectAll(TrainScore trainScore);
    
    int insert(TrainScore trainScore);
    
    int update(TrainScore trainScore);
    
    int deleteById(@Param("id") Long id);
    
    int deleteByIds(@Param("ids") Long[] ids);
    
    TrainScore checkScoreUnique(@Param("planId") Long planId, @Param("stuId") Long stuId);
}
