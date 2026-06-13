package com.eutmp.app.mapper;

import com.eutmp.app.bean.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生信息Mapper接口
 */
@Mapper
public interface StudentMapper {

    /**
     * 查询学生列表
     */
    List<Student> selectStudentList(Student student);

    /**
     * 根据ID查询学生
     */
    Student selectStudentById(Long id);

    /**
     * 新增学生
     */
    int insertStudent(Student student);

    /**
     * 修改学生
     */
    int updateStudent(Student student);

    /**
     * 删除学生
     */
    int deleteStudentById(Long id);

    /**
     * 批量删除学生
     */
    int deleteStudentByIds(Long[] ids);

    /**
     * 校验学号是否唯一
     */
    Student checkStudentNoUnique(String studentNo);
}
