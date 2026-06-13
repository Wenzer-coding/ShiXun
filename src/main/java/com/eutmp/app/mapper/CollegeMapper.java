package com.eutmp.app.mapper;

import com.eutmp.app.bean.College;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学院信息Mapper接口
 */
@Mapper
public interface CollegeMapper {
    
    /**
     * 根据ID查询学院
     */
    College selectById(@Param("id") Long id);
    
    /**
     * 查询所有学院
     */
    List<College> selectAll(College college);
    
    /**
     * 新增学院
     */
    int insert(College college);
    
    /**
     * 修改学院
     */
    int update(College college);
    
    /**
     * 删除学院
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除学院
     */
    int deleteByIds(@Param("ids") Long[] ids);
    
    /**
     * 校验学院编码是否唯一
     */
    College checkCollegeCodeUnique(College college);
}
