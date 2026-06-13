package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 耗材领用实体类
 */
@Data
public class TrainMaterialUse implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 领用记录ID */
    private Long id;

    /** 所属实训开班ID */
    private Long planId;

    /** 耗材ID */
    private Long materialId;

    /** 领用数量 */
    private Integer useNum;

    /** 领用教师ID */
    private Long useUser;

    /** 领用日期 */
    private LocalDate useDate;

    /** 使用说明 */
    private String useRemark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 开班名称(关联查询) */
    private String planName;

    /** 耗材名称(关联查询) */
    private String materialName;

    /** 领用教师姓名(关联查询) */
    private String useUserName;
}
