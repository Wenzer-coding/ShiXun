package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 实训成绩实体类
 */
@Data
public class TrainScore implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 成绩ID */
    private Long id;

    /** 实训开班ID */
    private Long planId;

    /** 学生ID */
    private Long stuId;

    /** 平时成绩(满分50) */
    private BigDecimal usualScore;

    /** 实训报告成绩(满分30) */
    private BigDecimal reportScore;

    /** 答辩考核成绩(满分20) */
    private BigDecimal defenseScore;

    /** 综合总分 */
    private BigDecimal totalScore;

    /** 等级:优秀/良好/中等/及格/不及格 */
    private String grade;

    /** 评语 */
    private String scoreRemark;

    /** 打分教师ID */
    private Long scoreTeacher;

    /** 打分时间 */
    private LocalDateTime scoreTime;

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
