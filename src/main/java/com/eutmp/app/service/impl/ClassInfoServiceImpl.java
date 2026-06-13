package com.eutmp.app.service.impl;

import com.eutmp.app.bean.ClassInfo;
import com.eutmp.app.mapper.ClassInfoMapper;
import com.eutmp.app.service.ClassInfoService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 班级信息Service实现类
 */
@Slf4j
@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    
    @Autowired
    private ClassInfoMapper classInfoMapper;
    
    @Override
    public PageResult<ClassInfo> selectByPage(Integer pageNum, Integer pageSize, ClassInfo classInfo) {
        log.info("分页查询班级: pageNum={}, pageSize={}, classInfo={}", pageNum, pageSize, classInfo);
        
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 查询数据
        List<ClassInfo> list = classInfoMapper.selectClassInfoList(classInfo);
        
        // 包装成PageInfo
        PageInfo<ClassInfo> pageInfo = new PageInfo<>(list);
        
        log.info("分页查询结果: total={}, pages={}", pageInfo.getTotal(), pageInfo.getPages());
        
        // 返回统一的分页结果
        return PageResult.of(
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
    }
    
    @Override
    public ClassInfo selectById(Long id) {
        return classInfoMapper.selectClassInfoById(id);
    }
    
    @Override
    public List<ClassInfo> selectAll() {
        return classInfoMapper.selectClassInfoList(new ClassInfo());
    }
    
    @Override
    public int insert(ClassInfo classInfo) {
        log.info("新增班级: classCode={}, className={}", classInfo.getClassCode(), classInfo.getClassName());
        
        // 设置默认值
        classInfo.setStatus(classInfo.getStatus() != null ? classInfo.getStatus() : 1);
        classInfo.setCreateTime(LocalDateTime.now());
        classInfo.setUpdateTime(LocalDateTime.now());
        
        // 插入数据库
        int result = classInfoMapper.insertClassInfo(classInfo);
        log.info("新增班级结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int update(ClassInfo classInfo) {
        log.info("修改班级: id={}, classCode={}", classInfo.getId(), classInfo.getClassCode());
        
        // 设置更新时间
        classInfo.setUpdateTime(LocalDateTime.now());
        
        // 更新数据库
        int result = classInfoMapper.updateClassInfo(classInfo);
        log.info("修改班级结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int deleteById(Long id) {
        log.info("删除班级: id={}", id);
        
        // 删除数据库记录
        int result = classInfoMapper.deleteClassInfoById(id);
        log.info("删除班级结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        log.info("批量删除班级: ids={}", (Object) ids);
        
        // 批量删除数据库记录
        int result = classInfoMapper.deleteClassInfoByIds(ids);
        log.info("批量删除班级结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public boolean checkClassCodeUnique(ClassInfo classInfo) {
        ClassInfo existClass = classInfoMapper.checkClassCodeUnique(classInfo.getClassCode());
        if (existClass != null && !existClass.getId().equals(classInfo.getId())) {
            return false;
        }
        return true;
    }
}
