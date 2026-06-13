package com.eutmp.app.service.impl;

import com.eutmp.app.bean.Student;
import com.eutmp.app.mapper.StudentMapper;
import com.eutmp.app.service.StudentService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 学生信息Service实现类
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private StudentMapper studentMapper;
    
    @Override
    public PageResult<Student> selectByPage(Integer pageNum, Integer pageSize, Student student) {
        log.info("分页查询学生: pageNum={}, pageSize={}, student={}", pageNum, pageSize, student);
        
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 查询数据
        List<Student> list = studentMapper.selectStudentList(student);
        
        // 包装成PageInfo
        PageInfo<Student> pageInfo = new PageInfo<>(list);
        
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
    public Student selectById(Long id) {
        return studentMapper.selectStudentById(id);
    }
    
    @Override
    public List<Student> selectAll() {
        return studentMapper.selectStudentList(new Student());
    }
    
    @Override
    public int insert(Student student) {
        log.info("新增学生: studentNo={}, studentName={}", student.getStudentNo(), student.getStudentName());
        
        // 设置默认值
        student.setStatus(student.getStatus() != null ? student.getStatus() : 1);
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        
        // 插入数据库
        int result = studentMapper.insertStudent(student);
        log.info("新增学生结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int update(Student student) {
        log.info("修改学生: id={}, studentNo={}", student.getId(), student.getStudentNo());
        
        // 设置更新时间
        student.setUpdateTime(LocalDateTime.now());
        
        // 更新数据库
        int result = studentMapper.updateStudent(student);
        log.info("修改学生结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int deleteById(Long id) {
        log.info("删除学生: id={}", id);
        
        // 删除数据库记录
        int result = studentMapper.deleteStudentById(id);
        log.info("删除学生结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public int deleteByIds(Long[] ids) {
        log.info("批量删除学生: ids={}", (Object) ids);
        
        // 批量删除数据库记录
        int result = studentMapper.deleteStudentByIds(ids);
        log.info("批量删除学生结果: {}", result > 0 ? "成功" : "失败");
        return result;
    }
    
    @Override
    public boolean checkStudentNoUnique(Student student) {
        Student existStudent = studentMapper.checkStudentNoUnique(student.getStudentNo());
        if (existStudent != null && !existStudent.getId().equals(student.getId())) {
            return false;
        }
        return true;
    }
}
