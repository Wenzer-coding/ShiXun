package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 成绩信息实体类
 */
@Data
public class Score implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 成绩ID
     */
    private Long id;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 考试成绩
     */
    private Double examScore;

    /**
     * 平时成绩
     */
    private Double usualScore;

    /**
     * 总成绩
     */
    private Double totalScore;

    /**
     * 学期 (如: 2024-2025-1)
     */
    private String semester;

    /**
     * 考试类型 (1期中考试 2期末考试)
     */
    private Integer examType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
