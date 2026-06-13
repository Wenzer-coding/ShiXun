package com.eutmp.app.service;

import com.eutmp.app.bean.TrainMaterialUse;
import com.eutmp.app.utils.PageResult;
import java.util.List;

public interface TrainMaterialUseService {
    PageResult<TrainMaterialUse> selectByPage(Integer pageNum, Integer pageSize, TrainMaterialUse trainMaterialUse);
    TrainMaterialUse selectById(Long id);
    List<TrainMaterialUse> selectAll(TrainMaterialUse trainMaterialUse);
    int insert(TrainMaterialUse trainMaterialUse);
    int update(TrainMaterialUse trainMaterialUse);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
}
