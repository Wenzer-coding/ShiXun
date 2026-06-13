package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程信息实体类
 */
@Data
public class Course implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    private Long id;

    /**
     * 课程编号
     */
    private String courseCode;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 学分
     */
    private Double credit;

    /**
     * 课时
     */
    private Integer hours;

    /**
     * 授课教师
     */
    private String teacher;

    /**
     * 所属专业ID
     */
    private Long majorId;

    /**
     * 课程类型 (1理论 2实践 3理实一体)
     */
    private Integer courseType;

    /**
     * 状态 (0停用 1启用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
