package com.eutmp.app.service;

import com.eutmp.app.bean.TrainMaterial;
import com.eutmp.app.utils.PageResult;
import java.util.List;

public interface TrainMaterialService {
    PageResult<TrainMaterial> selectByPage(Integer pageNum, Integer pageSize, TrainMaterial trainMaterial);
    TrainMaterial selectById(Long id);
    List<TrainMaterial> selectAll(TrainMaterial trainMaterial);
    int insert(TrainMaterial trainMaterial);
    int update(TrainMaterial trainMaterial);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
}
