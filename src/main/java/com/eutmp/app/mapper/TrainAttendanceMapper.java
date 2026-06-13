package com.eutmp.app.mapper;

import com.eutmp.app.bean.TrainAttendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实训考勤Mapper接口
 */
@Mapper
public interface TrainAttendanceMapper {

    TrainAttendance selectById(@Param("id") Long id);

    List<TrainAttendance> selectAll(TrainAttendance trainAttendance);

    int insert(TrainAttendance trainAttendance);

    int update(TrainAttendance trainAttendance);

    int deleteById(@Param("id") Long id);

    int deleteByIds(@Param("ids") Long[] ids);

    TrainAttendance checkAttendanceUnique(@Param("planId") Long planId, @Param("stuId") Long stuId, @Param("attendDate") String attendDate);

    List<TrainAttendance> selectByPlanIdAndDate(@Param("planId") Long planId, @Param("attendDate") String attendDate);

    TrainAttendance selectByStudentNoAndPlanDate(@Param("studentNo") String studentNo, @Param("planId") Long planId, @Param("attendDate") String attendDate);
}
