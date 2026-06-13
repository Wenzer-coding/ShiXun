package com.eutmp.app.mapper;

import com.eutmp.app.bean.ClassInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 班级信息Mapper接口
 */
@Mapper
public interface ClassInfoMapper {

    /**
     * 查询班级列表
     */
    List<ClassInfo> selectClassInfoList(ClassInfo classInfo);

    /**
     * 根据ID查询班级
     */
    ClassInfo selectClassInfoById(Long id);

    /**
     * 新增班级
     */
    int insertClassInfo(ClassInfo classInfo);

    /**
     * 修改班级
     */
    int updateClassInfo(ClassInfo classInfo);

    /**
     * 删除班级
     */
    int deleteClassInfoById(Long id);

    /**
     * 批量删除班级
     */
    int deleteClassInfoByIds(Long[] ids);

    /**
     * 校验班级编号是否唯一
     */
    ClassInfo checkClassCodeUnique(String classCode);
}
