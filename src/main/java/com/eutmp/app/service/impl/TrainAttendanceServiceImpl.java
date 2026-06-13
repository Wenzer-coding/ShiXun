package com.eutmp.app.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.eutmp.app.bean.Student;
import com.eutmp.app.bean.SysUser;
import com.eutmp.app.bean.TrainAttendance;
import com.eutmp.app.bean.TrainEnroll;
import com.eutmp.app.mapper.StudentMapper;
import com.eutmp.app.mapper.SysUserMapper;
import com.eutmp.app.mapper.TrainAttendanceMapper;
import com.eutmp.app.mapper.TrainEnrollMapper;
import com.eutmp.app.service.TrainAttendanceService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TrainAttendanceServiceImpl implements TrainAttendanceService {

    @Autowired
    private TrainAttendanceMapper trainAttendanceMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TrainEnrollMapper trainEnrollMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    private Long defaultCreatorId = null;

    @PostConstruct
    public void init() {
        try {
            SysUser admin = sysUserMapper.selectByUsername("admin");
            if (admin != null) {
                defaultCreatorId = admin.getId();
                log.info("学生签到默认登记人: id={}, username={}", defaultCreatorId, admin.getUsername());
            } else {
                List<SysUser> all = sysUserMapper.selectAll();
                if (!all.isEmpty()) {
                    defaultCreatorId = all.get(0).getId();
                    log.info("学生签到默认登记人(取第一个用户): id={}", defaultCreatorId);
                }
            }
        } catch (Exception e) {
            log.error("初始化默认登记人失败", e);
        }
    }

    @Override
    public PageResult<TrainAttendance> selectByPage(Integer pageNum, Integer pageSize, TrainAttendance trainAttendance) {
        PageHelper.startPage(pageNum, pageSize);
        List<TrainAttendance> list = trainAttendanceMapper.selectAll(trainAttendance);
        PageInfo<TrainAttendance> pageInfo = new PageInfo<>(list);
        return PageResult.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public TrainAttendance selectById(Long id) {
        return trainAttendanceMapper.selectById(id);
    }

    @Override
    public List<TrainAttendance> selectAll(TrainAttendance trainAttendance) {
        return trainAttendanceMapper.selectAll(trainAttendance);
    }

    @Override
    public int insert(TrainAttendance trainAttendance) {
        try {
            Long userId = Long.parseLong(String.valueOf(StpUtil.getLoginId()));
            trainAttendance.setCreateUser(userId);
        } catch (Exception e) {
            log.warn("获取当前登录用户失败", e);
        }
        trainAttendance.setCreateTime(LocalDateTime.now());
        trainAttendance.setUpdateTime(LocalDateTime.now());
        return trainAttendanceMapper.insert(trainAttendance);
    }

    @Override
    public int update(TrainAttendance trainAttendance) {
        trainAttendance.setUpdateTime(LocalDateTime.now());
        return trainAttendanceMapper.update(trainAttendance);
    }

    @Override
    public int deleteById(Long id) {
        return trainAttendanceMapper.deleteById(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return trainAttendanceMapper.deleteByIds(ids);
    }

    @Override
    public boolean checkAttendanceUnique(TrainAttendance trainAttendance) {
        TrainAttendance exist = trainAttendanceMapper.checkAttendanceUnique(
            trainAttendance.getPlanId(), trainAttendance.getStuId(), trainAttendance.getAttendDate().toString());
        return exist == null || exist.getId().equals(trainAttendance.getId());
    }

    @Override
    public List<TrainAttendance> selectByPlanIdAndDate(Long planId, String attendDate) {
        return trainAttendanceMapper.selectByPlanIdAndDate(planId, attendDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String studentSignIn(String studentNo, Long planId, String attendDate) {
        Student student = studentMapper.checkStudentNoUnique(studentNo);
        if (student == null) {
            throw new RuntimeException("学号不存在: " + studentNo);
        }

        TrainEnroll enroll = trainEnrollMapper.selectByPlanIdAndStuId(planId, student.getId());
        if (enroll == null) {
            throw new RuntimeException("该学生未报名此开班，请联系教师");
        }
        if (enroll.getAuditStatus() == null || enroll.getAuditStatus() != 2) {
            throw new RuntimeException("报名未审核通过，无法签到");
        }

        TrainAttendance exist = trainAttendanceMapper.selectByStudentNoAndPlanDate(studentNo, planId, attendDate);
        if (exist != null) {
            if (exist.getSignStatus() == 1) {
                return student.getStudentName() + "学生已签到";
            }
            exist.setSignStatus(1);
            exist.setSignTime(LocalDateTime.now());
            exist.setUpdateTime(LocalDateTime.now());
            trainAttendanceMapper.update(exist);
            log.info("学生签到更新: {}, 学号={}", student.getStudentName(), studentNo);
            return student.getStudentName() + "学生已签到";
        }

        TrainAttendance attendance = new TrainAttendance();
        attendance.setPlanId(planId);
        attendance.setStuId(student.getId());
        attendance.setAttendDate(LocalDate.parse(attendDate));
        attendance.setSignTime(LocalDateTime.now());
        attendance.setSignStatus(1);
        attendance.setCreateUser(defaultCreatorId);
        attendance.setCreateTime(LocalDateTime.now());
        attendance.setUpdateTime(LocalDateTime.now());
        trainAttendanceMapper.insert(attendance);

        log.info("学生签到成功: {}, 学号={}", student.getStudentName(), studentNo);
        return student.getStudentName() + "学生已签到";
    }

    @Override
    public Map<String, Object> getAttendanceStats(Long planId, String attendDate) {
        List<TrainAttendance> signedList = trainAttendanceMapper.selectByPlanIdAndDate(planId, attendDate);

        List<Map<String, Object>> signed = new ArrayList<>();
        for (TrainAttendance ta : signedList) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", ta.getId());
            item.put("studentName", ta.getStudentName());
            item.put("studentNo", ta.getStudentNo());
            item.put("signTime", ta.getSignTime() != null ? ta.getSignTime().toString() : "");
            item.put("signStatus", ta.getSignStatus());
            signed.add(item);
        }

        TrainEnroll filter = new TrainEnroll();
        filter.setPlanId(planId);
        filter.setAuditStatus(2);
        List<TrainEnroll> enrolledList = trainEnrollMapper.selectAll(filter);

        List<Map<String, Object>> notSigned = new ArrayList<>();
        for (TrainEnroll enroll : enrolledList) {
            boolean found = false;
            for (TrainAttendance ta : signedList) {
                if (ta.getStuId().equals(enroll.getStuId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Map<String, Object> item = new HashMap<>();
                item.put("studentName", enroll.getStudentName());
                item.put("studentNo", enroll.getStudentNo());
                notSigned.add(item);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalSigned", signedList.size());
        result.put("totalEnrolled", enrolledList.size());
        result.put("signed", signed);
        result.put("notSigned", notSigned);

        return result;
    }
}
