package com.eutmp.app.service;

import com.eutmp.app.bean.TrainPlan;
import com.eutmp.app.utils.PageResult;
import java.util.List;

public interface TrainPlanService {
    PageResult<TrainPlan> selectByPage(Integer pageNum, Integer pageSize, TrainPlan trainPlan);
    TrainPlan selectById(Long id);
    List<TrainPlan> selectAll(TrainPlan trainPlan);
    int insert(TrainPlan trainPlan);
    int update(TrainPlan trainPlan);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);

    int incrementEnrollNum(Long id);

    int decrementEnrollNum(Long id);
}
