package com.eutmp.app.mapper;

import com.eutmp.app.bean.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专业信息Mapper接口
 */
@Mapper
public interface MajorMapper {
    
    /**
     * 根据ID查询专业
     */
    Major selectById(@Param("id") Long id);
    
    /**
     * 查询所有专业
     */
    List<Major> selectAll(Major major);
    
    /**
     * 新增专业
     */
    int insert(Major major);
    
    /**
     * 修改专业
     */
    int update(Major major);
    
    /**
     * 删除专业
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除专业
     */
    int deleteByIds(@Param("ids") Long[] ids);
    
    /**
     * 校验专业编码是否唯一
     */
    Major checkMajorCodeUnique(Major major);
}
