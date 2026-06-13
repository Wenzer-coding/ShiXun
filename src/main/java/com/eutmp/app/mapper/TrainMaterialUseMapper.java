package com.eutmp.app.mapper;

import com.eutmp.app.bean.TrainMaterialUse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 耗材领用Mapper接口
 */
@Mapper
public interface TrainMaterialUseMapper {
    
    TrainMaterialUse selectById(@Param("id") Long id);
    
    List<TrainMaterialUse> selectAll(TrainMaterialUse trainMaterialUse);
    
    int insert(TrainMaterialUse trainMaterialUse);
    
    int update(TrainMaterialUse trainMaterialUse);
    
    int deleteById(@Param("id") Long id);
    
    int deleteByIds(@Param("ids") Long[] ids);
}
