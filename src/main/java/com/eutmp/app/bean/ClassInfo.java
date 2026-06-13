package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 班级信息实体类
 */
@Data
public class ClassInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
    private Long id;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级编号
     */
    private String classCode;

    /**
     * 所属专业
     */
    private String major;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 班主任
     */
    private String headTeacher;

    /**
     * 班级人数
     */
    private Integer studentCount;

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
