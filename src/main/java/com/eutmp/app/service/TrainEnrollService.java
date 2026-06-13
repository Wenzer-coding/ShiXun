package com.eutmp.app.service;

import com.eutmp.app.bean.TrainEnroll;
import com.eutmp.app.utils.PageResult;
import java.util.List;

public interface TrainEnrollService {
    PageResult<TrainEnroll> selectByPage(Integer pageNum, Integer pageSize, TrainEnroll trainEnroll);
    TrainEnroll selectById(Long id);
    List<TrainEnroll> selectAll(TrainEnroll trainEnroll);
    int insert(TrainEnroll trainEnroll);
    int update(TrainEnroll trainEnroll);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
    boolean checkEnrollUnique(TrainEnroll trainEnroll);

    void audit(Long id, Long auditUser, String auditRemark);

    void reject(Long id, Long auditUser, String auditRemark);
}
