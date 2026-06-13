package com.eutmp.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学院信息实体类
 */
@Data
public class College implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 学院主键ID */
    private Long id;

    /** 学院名称 */
    private String collegeName;

    /** 学院编码 */
    private String collegeCode;

    /** 院长ID(关联sys_user.id) */
    private Long deanId;

    /** 联系电话 */
    private String contactPhone;

    /** 联系邮箱 */
    private String contactEmail;

    /** 学院地址 */
    private String address;

    /** 学院简介 */
    private String intro;

    /** 排序 */
    private Integer sort;

    /** 状态(0停用 1启用) */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
