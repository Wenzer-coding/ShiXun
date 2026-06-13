package com.eutmp.app.service;

import com.eutmp.app.bean.TrainScore;
import com.eutmp.app.utils.PageResult;
import java.util.List;

public interface TrainScoreService {
    PageResult<TrainScore> selectByPage(Integer pageNum, Integer pageSize, TrainScore trainScore);
    TrainScore selectById(Long id);
    List<TrainScore> selectAll(TrainScore trainScore);
    int insert(TrainScore trainScore);
    int update(TrainScore trainScore);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
    boolean checkScoreUnique(TrainScore trainScore);
}
