package com.eutmp.app.mapper;

import com.eutmp.app.bean.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 成绩信息Mapper接口
 */
@Mapper
public interface ScoreMapper {

    /**
     * 查询成绩列表
     */
    List<Score> selectScoreList(Score score);

    /**
     * 根据ID查询成绩
     */
    Score selectScoreById(Long id);

    /**
     * 新增成绩
     */
    int insertScore(Score score);

    /**
     * 修改成绩
     */
    int updateScore(Score score);

    /**
     * 删除成绩
     */
    int deleteScoreById(Long id);

    /**
     * 批量删除成绩
     */
    int deleteScoreByIds(Long[] ids);

    /**
     * 查询学生某课程的成绩
     */
    Score selectScoreByStudentAndCourse(Long studentId, Long courseId);
}
