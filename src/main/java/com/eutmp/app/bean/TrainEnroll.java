package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实训报名实体类
 */
@Data
public class TrainEnroll implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 报名主键ID */
    private Long id;

    /** 实训开班ID */
    private Long planId;

    /** 学生ID */
    private Long stuId;

    /** 报名时间 */
    private LocalDateTime enrollTime;

    /** 审核状态:1待审核 2审核通过 3驳回 */
    private Integer auditStatus;

    /** 审核人ID */
    private Long auditUser;

    /** 审核时间 */
    private LocalDateTime auditTime;

    /** 审核备注 */
    private String auditRemark;

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
