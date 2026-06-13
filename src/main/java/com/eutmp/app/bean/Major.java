package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 专业信息实体类
 */
@Data
public class Major implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 专业主键ID */
    private Long id;

    /** 所属学院ID(关联sys_dept.id) */
    private Long deptId;

    /** 专业名称 */
    private String majorName;

    /** 专业编码 */
    private String majorCode;

    /** 学历层次:1专科 2本科 3研究生 */
    private Integer eduLevel;

    /** 学制(年) */
    private Integer studyYear;

    /** 排序 */
    private Integer sort;

    /** 状态(0停用 1启用) */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 学院名称(关联查询) */
    private String collegeName;
}
