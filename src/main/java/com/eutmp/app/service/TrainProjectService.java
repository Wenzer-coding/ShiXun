package com.eutmp.app.service;

import com.eutmp.app.bean.TrainProject;
import com.eutmp.app.utils.PageResult;
import java.util.List;

public interface TrainProjectService {
    PageResult<TrainProject> selectByPage(Integer pageNum, Integer pageSize, TrainProject trainProject);
    TrainProject selectById(Long id);
    List<TrainProject> selectAll(TrainProject trainProject);
    int insert(TrainProject trainProject);
    int update(TrainProject trainProject);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
    boolean checkProjectCodeUnique(TrainProject trainProject);
}
