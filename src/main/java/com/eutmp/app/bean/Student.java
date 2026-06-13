package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生信息实体类
 */
@Data
public class Student implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    private Long id;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 性别 (0女 1男)
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 班级名称(关联查询)
     */
    private String className;

    /**
     * 入学年份
     */
    private Integer enrollYear;

    /**
     * 状态 (0禁用 1正常)
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
