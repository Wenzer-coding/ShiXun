package com.eutmp.app.mapper;

import com.eutmp.app.bean.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 课程信息Mapper接口
 */
@Mapper
public interface CourseMapper {

    /**
     * 查询课程列表
     */
    List<Course> selectCourseList(Course course);

    /**
     * 根据ID查询课程
     */
    Course selectCourseById(Long id);

    /**
     * 新增课程
     */
    int insertCourse(Course course);

    /**
     * 修改课程
     */
    int updateCourse(Course course);

    /**
     * 删除课程
     */
    int deleteCourseById(Long id);

    /**
     * 批量删除课程
     */
    int deleteCourseByIds(Long[] ids);

    /**
     * 校验课程编号是否唯一
     */
    Course checkCourseCodeUnique(String courseCode);
}
