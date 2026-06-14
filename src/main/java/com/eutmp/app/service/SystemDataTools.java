package com.eutmp.app.service;

import com.eutmp.app.bean.*;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SystemDataTools {

    private final SysUserService sysUserService;
    private final StudentService studentService;
    private final CollegeService collegeService;
    private final MajorService majorService;
    private final ClassInfoService classInfoService;
    private final CourseService courseService;
    private final ScoreService scoreService;

    @Tool("查询所有学院信息")
    public String listColleges() {
        List<College> list = collegeService.selectAll(new College());
        return formatList("学院", list);
    }

    @Tool("查询所有专业信息")
    public String listMajors() {
        List<Major> list = majorService.selectAll(new Major());
        return formatList("专业", list);
    }

    @Tool("查询所有班级信息")
    public String listClasses() {
        List<ClassInfo> list = classInfoService.selectAll();
        return formatList("班级", list);
    }

    @Tool("查询所有课程信息")
    public String listCourses() {
        List<Course> list = courseService.selectAll();
        return formatList("课程", list);
    }

    @Tool("查询所有学生信息")
    public String listStudents() {
        List<Student> list = studentService.selectAll();
        if (list == null || list.isEmpty()) return "暂无学生数据";
        return list.stream()
                .map(s -> String.format("[学生] ID=%d, 学号=%s, 姓名=%s, 性别=%s, 班级ID=%s",
                        s.getId(), s.getStudentNo(), s.getStudentName(), s.getGender(),
                        s.getClassId() == null ? "无" : s.getClassId()))
                .collect(Collectors.joining("\n"));
    }

    @Tool("查询所有系统用户信息")
    public String listUsers() {
        List<SysUser> list = sysUserService.selectAll();
        if (list == null || list.isEmpty()) return "暂无系统用户数据";
        return list.stream()
                .map(u -> String.format("[用户] ID=%d, 用户名=%s, 昵称=%s, 邮箱=%s, 手机号=%s, 状态=%s, 部门ID=%s",
                        u.getId(), u.getUsername(), u.getNickName(), u.getEmail(),
                        u.getPhone(), u.getStatus() == 1 ? "正常" : "禁用",
                        u.getDeptId() == null ? "无" : u.getDeptId()))
                .collect(Collectors.joining("\n"));
    }

    @Tool("查询成绩统计信息")
    public String getScoreStats() {
        List<Score> all = scoreService.selectAll();
        if (all == null || all.isEmpty()) return "暂无成绩数据";
        long count = all.size();
        double avg = all.stream().mapToDouble(s -> s.getTotalScore() != null ? s.getTotalScore() : 0).average().orElse(0);
        double max = all.stream().mapToDouble(s -> s.getTotalScore() != null ? s.getTotalScore() : 0).max().orElse(0);
        double min = all.stream().mapToDouble(s -> s.getTotalScore() != null ? s.getTotalScore() : 0).min().orElse(0);
        long pass = all.stream().filter(s -> s.getTotalScore() != null && s.getTotalScore() >= 60).count();
        return String.format("成绩统计：总记录数=%d, 平均分=%.1f, 最高分=%.1f, 最低分=%.1f, 及格人数=%d, 及格率=%.1f%%",
                count, avg, max, min, pass, count > 0 ? pass * 100.0 / count : 0);
    }

    @Tool("获取系统数据总览统计")
    public String getSystemOverview() {
        long userCount = sysUserService.selectAll().size();
        long studentCount = studentService.selectAll().size();
        long collegeCount = collegeService.selectAll(new College()).size();
        long majorCount = majorService.selectAll(new Major()).size();
        long classCount = classInfoService.selectAll().size();
        long courseCount = courseService.selectAll().size();
        return String.format(
                "系统数据概览：\n" +
                "- 🏫 学院数：%d\n" +
                "- 📚 专业数：%d\n" +
                "- 🏠 班级数：%d\n" +
                "- 📖 课程数：%d\n" +
                "- 👤 系统用户数：%d\n" +
                "- 👨‍🎓 学生数：%d",
                collegeCount, majorCount, classCount, courseCount, userCount, studentCount);
    }

    private String formatList(String name, List<?> list) {
        if (list == null || list.isEmpty()) return "暂无" + name + "数据";
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
