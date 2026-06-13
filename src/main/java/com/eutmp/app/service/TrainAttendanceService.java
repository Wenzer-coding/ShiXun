package com.eutmp.app.service;

import com.eutmp.app.bean.TrainAttendance;
import com.eutmp.app.utils.PageResult;

import java.util.List;
import java.util.Map;

public interface TrainAttendanceService {
    PageResult<TrainAttendance> selectByPage(Integer pageNum, Integer pageSize, TrainAttendance trainAttendance);
    TrainAttendance selectById(Long id);
    List<TrainAttendance> selectAll(TrainAttendance trainAttendance);
    int insert(TrainAttendance trainAttendance);
    int update(TrainAttendance trainAttendance);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
    boolean checkAttendanceUnique(TrainAttendance trainAttendance);

    List<TrainAttendance> selectByPlanIdAndDate(Long planId, String attendDate);

    String studentSignIn(String studentNo, Long planId, String attendDate);

    Map<String, Object> getAttendanceStats(Long planId, String attendDate);
}
