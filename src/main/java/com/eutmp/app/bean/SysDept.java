package com.eutmp.app.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 部门表实体类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDept {
    /**
     * 部门ID
     */
    private Long id;
    
    /**
     * 父部门ID，0=顶级部门
     */
    private Long parentId;
    
    /**
     * 部门名称
     */
    private String deptName;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 0停用 1启用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 更新时间
     */
    private String updateTime;
    
    /**
     * 子部门列表（树形结构使用）
     */
    private List<SysDept> children;
}
