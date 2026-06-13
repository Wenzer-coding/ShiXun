package com.eutmp.app.controller;

import com.eutmp.app.aop.OperLog;
import com.eutmp.app.bean.TrainAttendance;
import com.eutmp.app.service.TrainAttendanceService;
import com.eutmp.app.utils.PageResult;
import com.eutmp.app.utils.IpUtil;
import com.eutmp.app.utils.QrCodeUtil;
import com.eutmp.app.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/attendance")
public class TrainAttendanceController {

    @Autowired
    private TrainAttendanceService trainAttendanceService;

    @GetMapping("/list")
    public Result<PageResult<TrainAttendance>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long planId,
            @RequestParam(required = false) Integer signStatus,
            @RequestParam(required = false) String attendDate) {
        try {
            TrainAttendance attendance = new TrainAttendance();
            attendance.setPlanId(planId);
            attendance.setSignStatus(signStatus);
            if (attendDate != null) {
                attendance.setAttendDate(java.time.LocalDate.parse(attendDate));
            }
            return Result.success(trainAttendanceService.selectByPage(pageNum, pageSize, attendance));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<TrainAttendance> getById(@PathVariable Long id) {
        try {
            return Result.success(trainAttendanceService.selectById(id));
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @PostMapping
    @OperLog(module = "考勤管理", operType = 1)
    public Result<String> insert(@RequestBody TrainAttendance trainAttendance) {
        try {
            trainAttendanceService.insert(trainAttendance);
            return Result.success("新增成功");
        } catch (Exception e) {
            return Result.error("新增失败: " + e.getMessage());
        }
    }

    @PutMapping
    @OperLog(module = "考勤管理", operType = 2)
    public Result<String> update(@RequestBody TrainAttendance trainAttendance) {
        try {
            trainAttendanceService.update(trainAttendance);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @OperLog(module = "考勤管理", operType = 3)
    public Result<String> delete(@PathVariable Long id) {
        try {
            trainAttendanceService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/batch")
    @OperLog(module = "考勤管理", operType = 3)
    public Result<String> deleteBatch(@RequestBody Long[] ids) {
        try {
            trainAttendanceService.deleteByIds(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败: " + e.getMessage());
        }
    }

    @GetMapping("/qrcode")
    public void generateQrCode(
            @RequestParam Long planId,
            @RequestParam String date,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            int port = request.getServerPort();
            String ip = IpUtil.getLanIp();
            String signInUrl = "http://" + ip + ":" + port + "/attendance/signin?planId=" + planId + "&date=" + date;
            log.info("生成签到二维码 - URL: {}", signInUrl);

            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            byte[] qrBytes = QrCodeUtil.generateQrCode(signInUrl, 400, 400);
            response.getOutputStream().write(qrBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("生成二维码失败", e);
            response.setStatus(500);
        }
    }

    @PostMapping("/signin")
    public Result<Map<String, Object>> studentSignIn(@RequestBody Map<String, String> params) {
        try {
            String studentNo = params.get("studentNo");
            Long planId = Long.parseLong(params.get("planId"));
            String date = params.get("date");

            if (studentNo == null || studentNo.trim().isEmpty()) {
                return Result.error("请输入学号");
            }

            String message = trainAttendanceService.studentSignIn(studentNo.trim(), planId, date);

            Map<String, Object> result = new HashMap<>();
            result.put("message", message);
            result.put("studentNo", studentNo);
            return Result.success(result);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("签到失败", e);
            return Result.error("签到失败，请稍后重试");
        }
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats(
            @RequestParam Long planId,
            @RequestParam String date) {
        try {
            Map<String, Object> stats = trainAttendanceService.getAttendanceStats(planId, date);
            return Result.success(stats);
        } catch (Exception e) {
            return Result.error("查询统计失败: " + e.getMessage());
        }
    }
}
