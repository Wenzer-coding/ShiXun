package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实训考勤实体类
 */
@Data
public class TrainAttendance implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 考勤ID */
    private Long id;

    /** 实训开班ID */
    private Long planId;

    /** 学生ID */
    private Long stuId;

    /** 考勤日期 */
    private LocalDate attendDate;

    /** 实际签到时间 */
    private LocalDateTime signTime;

    /** 考勤状态:1正常 2迟到 3早退 4缺勤 5请假 */
    private Integer signStatus;

    /** 请假事由 */
    private String leaveRemark;

    /** 登记教师ID */
    private Long createUser;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 开班名称(关联查询) */
    private String planName;

    /** 学生姓名(关联查询) */
    private String studentName;

    /** 学生学号(关联查询) */
    private String studentNo;
}
