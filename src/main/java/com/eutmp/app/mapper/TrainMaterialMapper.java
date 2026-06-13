package com.eutmp.app.mapper;

import com.eutmp.app.bean.TrainMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实训耗材Mapper接口
 */
@Mapper
public interface TrainMaterialMapper {
    
    TrainMaterial selectById(@Param("id") Long id);
    
    List<TrainMaterial> selectAll(TrainMaterial trainMaterial);
    
    int insert(TrainMaterial trainMaterial);
    
    int update(TrainMaterial trainMaterial);
    
    int deleteById(@Param("id") Long id);
    
    int deleteByIds(@Param("ids") Long[] ids);
}
