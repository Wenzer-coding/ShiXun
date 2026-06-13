package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实训开班计划实体类
 */
@Data
public class TrainPlan implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 实训开班ID */
    private Long id;

    /** 关联实训项目ID */
    private Long projectId;

    /** 开班名称 */
    private String planName;

    /** 学年学期(2025-2026上) */
    private String termYear;

    /** 开始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 实训地点 */
    private String trainAddress;

    /** 主讲指导教师ID */
    private Long teacherId;

    /** 辅助指导教师ID */
    private Long secondTeacherId;

    /** 最大容纳学生人数 */
    private Integer maxStudent;

    /** 已报名人数 */
    private Integer enrollNum;

    /** 报名开始时间 */
    private LocalDate enrollStart;

    /** 报名截止时间 */
    private LocalDate enrollEnd;

    /** 状态:1待报名 2报名中 3进行中 4已结束 5取消 */
    private Integer planStatus;

    /** 备注说明 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 项目名称(关联查询) */
    private String projectName;

    /** 主讲教师姓名(关联查询) */
    private String teacherName;
}
