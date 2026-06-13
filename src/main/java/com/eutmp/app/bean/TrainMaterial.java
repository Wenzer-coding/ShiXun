package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 实训耗材实体类
 */
@Data
public class TrainMaterial implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 耗材ID */
    private Long id;

    /** 耗材名称 */
    private String materialName;

    /** 规格型号 */
    private String spec;

    /** 计量单位 */
    private String unit;

    /** 采购单价 */
    private BigDecimal price;

    /** 当前库存数量 */
    private Integer stockNum;

    /** 库存预警值 */
    private Integer warnNum;

    /** 备注 */
    private String remark;

    /** 状态(0停用 1正常) */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
