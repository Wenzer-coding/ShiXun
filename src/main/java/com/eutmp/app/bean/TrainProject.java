package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实训项目实体类
 */
@Data
public class TrainProject implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 实训项目ID */
    private Long id;

    /** 项目名称 */
    private String projectName;

    /** 项目编号 */
    private String projectCode;

    /** 项目类型:1校内实训 2校外顶岗 3竞赛实训 4校企合作实训 */
    private Integer projectType;

    /** 实训周期(天) */
    private Integer trainCycle;

    /** 实训学分 */
    private BigDecimal credit;

    /** 人均耗材费用 */
    private BigDecimal cost;

    /** 实训内容简介 */
    private String content;

    /** 实训培养目标 */
    private String target;

    /** 限定适用专业ID(多个逗号分隔) */
    private String limitMajor;

    /** 状态(0停用 1启用) */
    private Integer status;

    /** 创建人(sys_user.id) */
    private Long createUser;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
