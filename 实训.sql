-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        11.4.2-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 eutmp 的数据库结构
CREATE DATABASE IF NOT EXISTS `eutmp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `eutmp`;

-- 导出  表 eutmp.class_info 结构
CREATE TABLE IF NOT EXISTS `class_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `class_name` varchar(100) NOT NULL COMMENT '班级名称',
  `class_code` varchar(50) NOT NULL COMMENT '班级编号',
  `major` varchar(100) DEFAULT NULL COMMENT '所属专业',
  `grade` int(11) DEFAULT NULL COMMENT '年级',
  `head_teacher` varchar(50) DEFAULT NULL COMMENT '班主任',
  `student_count` int(11) DEFAULT 0 COMMENT '班级人数',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 (0停用 1启用)',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_class_code` (`class_code`),
  KEY `idx_grade` (`grade`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班级信息表';

-- 正在导出表  eutmp.class_info 的数据：~3 rows (大约)
INSERT INTO `class_info` (`id`, `class_name`, `class_code`, `major`, `grade`, `head_teacher`, `student_count`, `status`, `create_time`, `update_time`) VALUES
	(1, '计算机2024-1班', 'CS202401', '计算机科学与技术', 2024, '张老师', 0, 1, '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(2, '计算机2024-2班', 'CS202402', '计算机科学与技术', 2024, '李老师', 0, 1, '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(3, '软件工程2024-1班', 'SE202401', '软件工程', 2024, '王老师', 0, 1, '2026-06-05 15:44:31', '2026-06-05 15:44:31');

-- 导出  表 eutmp.course 结构
CREATE TABLE IF NOT EXISTS `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_code` varchar(50) NOT NULL COMMENT '课程编号',
  `course_name` varchar(100) NOT NULL COMMENT '课程名称',
  `credit` double DEFAULT NULL COMMENT '学分',
  `hours` int(11) DEFAULT NULL COMMENT '课时',
  `teacher` varchar(50) DEFAULT NULL COMMENT '授课教师',
  `major_id` bigint(20) DEFAULT NULL COMMENT '所属专业ID',
  `course_type` tinyint(4) DEFAULT NULL COMMENT '课程类型 (1理论 2实践 3理实一体)',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 (0停用 1启用)',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_code` (`course_code`),
  KEY `idx_course_type` (`course_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程信息表';

-- 正在导出表  eutmp.course 的数据：~4 rows (大约)
INSERT INTO `course` (`id`, `course_code`, `course_name`, `credit`, `hours`, `teacher`, `course_type`, `major_id`, `status`, `create_time`, `update_time`) VALUES
	(1, 'CS101', 'Java程序设计', 4, 64, '张老师', 1, 1, 1, '2026-06-05 15:44:31', '2026-06-05 16:03:37'),
	(2, 'CS102', '数据库原理', 3.5, 56, '李老师', 1, 1, 1, '2026-06-05 15:44:31', '2026-06-05 16:03:37'),
	(3, 'CS103', 'Web前端开发', 3, 48, '王老师', 2, 2, 1, '2026-06-05 15:44:31', '2026-06-05 16:03:37'),
	(4, 'CS104', '数据结构', 4, 64, '赵老师', 3, 1, 1, '2026-06-05 15:44:31', '2026-06-05 16:03:37');

-- 导出  表 eutmp.edu_class 结构
CREATE TABLE IF NOT EXISTS `edu_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `major_id` bigint(20) NOT NULL COMMENT '所属专业ID，edu_major.id',
  `class_name` varchar(50) NOT NULL COMMENT '班级名称(计科2301)',
  `class_code` varchar(30) NOT NULL COMMENT '班级编码',
  `entrance_year` int(11) NOT NULL COMMENT '入学年份',
  `head_teacher_id` bigint(20) DEFAULT NULL COMMENT '班主任ID(教师sys_user.id)',
  `student_num` int(11) DEFAULT 0 COMMENT '在册人数',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0停用 1正常',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_class_code` (`class_code`),
  KEY `idx_major` (`major_id`),
  KEY `idx_teacher` (`head_teacher_id`),
  CONSTRAINT `fk_class_major` FOREIGN KEY (`major_id`) REFERENCES `edu_major` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_class_teacher` FOREIGN KEY (`head_teacher_id`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班级档案表';

-- 正在导出表  eutmp.edu_class 的数据：~4 rows (大约)
INSERT INTO `edu_class` (`id`, `major_id`, `class_name`, `class_code`, `entrance_year`, `head_teacher_id`, `student_num`, `status`, `create_time`, `update_time`) VALUES
	(1, 1, '计算机2024-1班', 'CS202401', 2024, NULL, 0, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(2, 1, '计算机2024-2班', 'CS202402', 2024, NULL, 0, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(3, 2, '软件工程2024-1班', 'SE202401', 2024, NULL, 0, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(4, 4, '工商管理2024-1班', 'BA202401', 2024, NULL, 0, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37');

-- 导出  表 eutmp.edu_major 结构
CREATE TABLE IF NOT EXISTS `edu_major` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专业主键ID',
  `dept_id` bigint(20) NOT NULL COMMENT '所属院系ID，关联sys_dept.id',
  `major_name` varchar(60) NOT NULL COMMENT '专业名称',
  `major_code` varchar(30) NOT NULL COMMENT '专业编码(国标专业代码)',
  `edu_level` tinyint(4) NOT NULL COMMENT '学历层次:1专科 2本科 3研究生',
  `study_year` tinyint(4) NOT NULL DEFAULT 4 COMMENT '学制(年)',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0停用 1启用',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_major_code` (`major_code`),
  KEY `idx_dept` (`dept_id`),
  CONSTRAINT `fk_major_dept` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='专业信息表';

-- 正在导出表  eutmp.edu_major 的数据：~6 rows (大约)
INSERT INTO `edu_major` (`id`, `dept_id`, `major_name`, `major_code`, `edu_level`, `study_year`, `sort`, `status`, `create_time`, `update_time`) VALUES
	(1, 40, '计算机科学与技术', 'CS001', 2, 4, 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(2, 41, '软件工程', 'SE002', 2, 4, 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(3, 42, '网络工程', 'NE003', 2, 4, 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(4, 43, '工商管理', 'BA004', 2, 4, 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(5, 44, '会计学', 'ACC005', 2, 4, 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(6, 45, '视觉传达设计', 'VC006', 2, 4, 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37');

-- 导出  表 eutmp.edu_student 结构
CREATE TABLE IF NOT EXISTS `edu_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学生主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '关联系统用户ID(sys_user.id，学生登录账号)',
  `class_id` bigint(20) NOT NULL COMMENT '所属班级ID edu_class.id',
  `stu_no` varchar(30) NOT NULL COMMENT '学号(唯一)',
  `real_name` varchar(30) NOT NULL COMMENT '学生真实姓名',
  `sex` tinyint(4) DEFAULT 0 COMMENT '0未知 1男 2女',
  `id_card` varchar(20) DEFAULT '' COMMENT '身份证号',
  `phone` varchar(20) DEFAULT '' COMMENT '学生手机号',
  `address` varchar(255) DEFAULT '' COMMENT '家庭住址',
  `in_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '在校状态:1在读 2休学 3毕业 4退学',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_stu_no` (`stu_no`),
  UNIQUE KEY `uk_user_student` (`user_id`),
  KEY `idx_class` (`class_id`),
  CONSTRAINT `fk_student_class` FOREIGN KEY (`class_id`) REFERENCES `edu_class` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_student_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生基础信息表';

-- 正在导出表  eutmp.edu_student 的数据：~0 rows (大约)

-- 导出  表 eutmp.score 结构
CREATE TABLE IF NOT EXISTS `score` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `exam_score` double DEFAULT NULL COMMENT '考试成绩',
  `usual_score` double DEFAULT NULL COMMENT '平时成绩',
  `total_score` double DEFAULT NULL COMMENT '总成绩',
  `semester` varchar(50) DEFAULT NULL COMMENT '学期 (如: 2024-2025-1)',
  `exam_type` tinyint(4) DEFAULT NULL COMMENT '考试类型 (1期中考试 2期末考试)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_course` (`student_id`,`course_id`),
  KEY `idx_semester` (`semester`),
  KEY `idx_exam_type` (`exam_type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='成绩信息表';

-- 正在导出表  eutmp.score 的数据：~5 rows (大约)
INSERT INTO `score` (`id`, `student_id`, `course_id`, `exam_score`, `usual_score`, `total_score`, `semester`, `exam_type`, `remark`, `create_time`, `update_time`) VALUES
	(1, 1, 1, 85, 90, 87, '2024-2025-1', 2, '期末考试', '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(2, 1, 2, 78, 85, 80.8, '2024-2025-1', 2, '期末考试', '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(3, 2, 1, 92, 88, 90.4, '2024-2025-1', 2, '期末考试', '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(4, 2, 2, 88, 92, 89.6, '2024-2025-1', 2, '期末考试', '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(5, 3, 1, 75, 80, 77, '2024-2025-1', 2, '期末考试', '2026-06-05 15:44:31', '2026-06-05 15:44:31');

-- 导出  表 eutmp.student 结构
CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `student_no` varchar(50) NOT NULL COMMENT '学号',
  `student_name` varchar(50) NOT NULL COMMENT '学生姓名',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别 (0女 1男)',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `class_id` bigint(20) DEFAULT NULL COMMENT '班级ID',
  `enroll_year` int(11) DEFAULT NULL COMMENT '入学年份',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 (0禁用 1正常)',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_no` (`student_no`),
  KEY `idx_class_id` (`class_id`),
  KEY `idx_enroll_year` (`enroll_year`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生信息表';

-- 正在导出表  eutmp.student 的数据：~5 rows (大约)
INSERT INTO `student` (`id`, `student_no`, `student_name`, `gender`, `phone`, `email`, `class_id`, `enroll_year`, `status`, `create_time`, `update_time`) VALUES
	(1, '2024001', '张三', 1, '13800138001', 'zhangsan@example.com', 1, 2024, 1, '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(2, '2024002', '李四', 0, '13800138002', 'lisi@example.com', 1, 2024, 1, '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(3, '2024003', '王五', 1, '13800138003', 'wangwu@example.com', 2, 2024, 1, '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(4, '2024004', '赵六', 0, '13800138004', 'zhaoliu@example.com', 2, 2024, 1, '2026-06-05 15:44:31', '2026-06-05 15:44:31'),
	(5, '2024005', '孙七', 1, '13800138005', 'sunqi@example.com', 3, 2024, 1, '2026-06-05 15:44:31', '2026-06-05 15:44:31');

-- 导出  表 eutmp.sys_dept 结构
CREATE TABLE IF NOT EXISTS `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父部门ID，0=顶级部门',
  `dept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0停用 1启用',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_parent` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';

-- 正在导出表  eutmp.sys_dept 的数据：~14 rows (大约)
INSERT INTO `sys_dept` (`id`, `parent_id`, `dept_name`, `sort`, `status`, `create_time`, `update_time`) VALUES
	(20, 0, '达优信息科技有限公司', 0, 1, '2026-06-05 07:34:20', '2026-06-05 07:34:20'),
	(21, 20, '人事部', 0, 1, '2026-06-05 07:34:53', '2026-06-05 07:34:53'),
	(22, 20, '财务部', 0, 1, '2026-06-05 07:35:01', '2026-06-05 07:35:01'),
	(23, 20, '市场部', 0, 1, '2026-06-05 07:35:08', '2026-06-05 07:35:08'),
	(24, 20, '教研部', 0, 1, '2026-06-05 07:35:19', '2026-06-05 07:35:19'),
	(30, 0, '计算机与信息学院', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(31, 0, '经济管理学院', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(32, 0, '艺术与传媒学院', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(40, 30, '计算机科学与技术', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(41, 30, '软件工程', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(42, 30, '网络工程', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(43, 31, '工商管理', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(44, 31, '会计学', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(45, 32, '视觉传达设计', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37');

-- 导出  表 eutmp.sys_oper_log 结构
CREATE TABLE IF NOT EXISTS `sys_oper_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `username` varchar(50) DEFAULT NULL COMMENT '操作账号',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '操作人昵称',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '操作人所属部门',
  `oper_module` varchar(50) NOT NULL COMMENT '操作模块：用户管理/角色管理',
  `oper_type` tinyint(4) NOT NULL COMMENT '1新增 2修改 3删除 4查询 5导入6导出',
  `oper_desc` varchar(255) DEFAULT '' COMMENT '操作描述',
  `request_url` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `request_method` varchar(20) DEFAULT NULL COMMENT '请求方式GET/POST',
  `oper_ip` varchar(50) DEFAULT NULL COMMENT '操作IP地址',
  `oper_params` text DEFAULT NULL COMMENT '请求参数',
  `json_result` text DEFAULT NULL COMMENT '返回结果',
  `cost_time` bigint(20) DEFAULT 0 COMMENT '耗时ms',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '1成功 0失败',
  `error_msg` text DEFAULT NULL COMMENT '异常信息',
  `create_time` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`user_id`),
  KEY `idx_ctime` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统操作日志表';

-- 正在导出表  eutmp.sys_oper_log 的数据：~117 rows (大约)
INSERT INTO `sys_oper_log` (`id`, `user_id`, `username`, `nick_name`, `dept_id`, `oper_module`, `oper_type`, `oper_desc`, `request_url`, `request_method`, `oper_ip`, `oper_params`, `json_result`, `cost_time`, `status`, `error_msg`, `create_time`) VALUES
	(16, 2, 'admin', '超级管理员', 1, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', NULL, 1, NULL, '2026-06-04 10:23:01'),
	(17, 2, 'admin', '超级管理员', 1, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', NULL, 1, NULL, '2026-06-04 10:23:18'),
	(18, 2, 'admin', '超级管理员', 1, '部门管理', 3, '删除部门', '/api/dept/13', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除部门成功","data":null,"success":true}', NULL, 1, NULL, '2026-06-04 10:35:52'),
	(19, 2, 'admin', '超级管理员', 1, '部门管理', 3, '删除部门', '/api/dept/1', 'DELETE', '127.0.0.1', NULL, '{"code":500,"message":"删除部门失败: 该部门下还有子部门，无法删除","data":null,"success":false}', NULL, 1, NULL, '2026-06-04 10:35:56'),
	(20, 2, 'admin', '超级管理员', 1, '部门管理', 3, '删除部门', '/api/dept/5', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除部门成功","data":null,"success":true}', NULL, 1, NULL, '2026-06-04 10:36:02'),
	(51, 2, 'admin', '超级管理员', 12, '操作日志', 3, '批量删除日志', '/api/operlog/batch', 'DELETE', '127.0.0.1', '{"ids[]":["50","29","28","27","26","25","24","23","22","21"]}', '{"code":200,"message":"批量删除成功","data":null,"success":true}', 7, 1, NULL, '2026-06-04 15:42:11'),
	(52, 2, 'admin', '超级管理员', 12, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增用户成功","data":null,"success":true}', 7, 1, NULL, '2026-06-04 18:26:23'),
	(53, 2, 'admin', '超级管理员', 12, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增用户成功","data":null,"success":true}', 6, 1, NULL, '2026-06-04 18:28:40'),
	(54, 2, 'admin', '超级管理员', 12, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增用户成功","data":null,"success":true}', 3, 1, NULL, '2026-06-04 18:29:18'),
	(55, 2, 'admin', '超级管理员', 12, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"修改用户成功","data":null,"success":true}', 18, 1, NULL, '2026-06-04 18:30:52'),
	(56, 2, 'admin', '超级管理员', 12, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"修改用户成功","data":null,"success":true}', 7, 1, NULL, '2026-06-04 18:31:03'),
	(57, 2, 'admin', '超级管理员', 12, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"修改用户成功","data":null,"success":true}', 5, 1, NULL, '2026-06-04 18:31:15'),
	(58, 2, 'admin', '超级管理员', 12, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增用户成功","data":null,"success":true}', 14, 1, NULL, '2026-06-04 18:49:59'),
	(59, 2, 'admin', '超级管理员', 12, '用户管理', 3, '删除用户', '/api/user/6', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除用户成功","data":null,"success":true}', 13, 1, NULL, '2026-06-04 19:15:27'),
	(60, 2, 'admin', '超级管理员', 12, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增用户成功","data":null,"success":true}', 13, 1, NULL, '2026-06-04 19:16:14'),
	(61, 2, 'admin', '超级管理员', 12, '用户管理', 3, '删除用户', '/api/user/7', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除用户成功","data":null,"success":true}', 8, 1, NULL, '2026-06-04 19:16:31'),
	(62, 2, 'admin', '超级管理员', 12, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 24, 1, NULL, '2026-06-04 19:45:08'),
	(63, 2, 'admin', '超级管理员', 12, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 292, 1, NULL, '2026-06-04 20:29:30'),
	(64, 2, 'admin', '超级管理员', 12, '部门管理', 3, '删除部门', '/api/dept/17', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除部门成功","data":null,"success":true}', 33, 1, NULL, '2026-06-05 07:32:56'),
	(65, 2, 'admin', '超级管理员', 12, '部门管理', 3, '删除部门', '/api/dept/15', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除部门成功","data":null,"success":true}', 24, 1, NULL, '2026-06-05 07:32:58'),
	(66, 2, 'admin', '超级管理员', 12, '部门管理', 3, '删除部门', '/api/dept/14', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除部门成功","data":null,"success":true}', 24, 1, NULL, '2026-06-05 07:33:00'),
	(67, 2, 'admin', '超级管理员', 12, '部门管理', 3, '删除部门', '/api/dept/12', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除部门成功","data":null,"success":true}', 11, 1, NULL, '2026-06-05 07:33:02'),
	(68, 2, 'admin', '超级管理员', 12, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', 13, 1, NULL, '2026-06-05 07:33:20'),
	(69, 2, 'admin', '超级管理员', 12, '部门管理', 3, '删除部门', '/api/dept/19', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除部门成功","data":null,"success":true}', 16, 1, NULL, '2026-06-05 07:33:35'),
	(70, 2, 'admin', '超级管理员', 12, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', 10, 1, NULL, '2026-06-05 07:34:20'),
	(71, 2, 'admin', '超级管理员', 12, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', 11, 1, NULL, '2026-06-05 07:34:53'),
	(72, 2, 'admin', '超级管理员', 12, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', 6, 1, NULL, '2026-06-05 07:35:01'),
	(73, 2, 'admin', '超级管理员', 12, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', 6, 1, NULL, '2026-06-05 07:35:08'),
	(74, 2, 'admin', '超级管理员', 12, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', 12, 1, NULL, '2026-06-05 07:35:19'),
	(75, 2, 'admin', '超级管理员', 12, '部门管理', 1, '新增部门', '/api/dept', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"新增部门成功","data":null,"success":true}', 6, 1, NULL, '2026-06-05 07:35:30'),
	(76, 2, 'admin', '超级管理员', 12, '部门管理', 3, '删除部门', '/api/dept/25', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"删除部门成功","data":null,"success":true}', 11, 1, NULL, '2026-06-05 07:35:46'),
	(77, 2, 'admin', '超级管理员', 12, '用户管理', 3, '删除用户', '/api/user/5', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除用户成功","success":true}', 13, 1, NULL, '2026-06-05 07:36:06'),
	(78, 2, 'admin', '超级管理员', 12, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增用户成功","success":true}', 35, 1, NULL, '2026-06-05 07:37:11'),
	(79, 2, 'admin', '超级管理员', 12, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 41, 1, NULL, '2026-06-05 07:42:48'),
	(80, 2, 'admin', '超级管理员', 21, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 18, 1, NULL, '2026-06-05 07:43:03'),
	(81, 2, 'admin', '超级管理员', 21, '用户管理', 3, '批量删除用户角色', '/api/user/8/roles', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"批量删除角色成功","success":true}', 20, 1, NULL, '2026-06-05 07:55:02'),
	(82, 2, 'admin', '超级管理员', 21, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 28, 1, NULL, '2026-06-05 07:55:28'),
	(83, 2, 'admin', '超级管理员', 21, '用户管理', 3, '批量删除用户角色', '/api/user/8/roles', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"批量删除角色成功","success":true}', 10, 1, NULL, '2026-06-05 08:09:41'),
	(84, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"45.53 MB","totalMemory":"120.00 MB","memoryUsage":"1.13%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"74.47 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.34 GB","freeSpace":"167.22 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"computerName":"LENOVO 20R7A018CD","osArch":"64位","systemUptime":"56分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.81 GB","totalMemory":"15.71 GB","memoryUsage":"81.52%","availableMemory":"2.90 GB"},"cpu":{"cpuUsage":"7.37%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1869, 1, NULL, '2026-06-05 08:25:07'),
	(85, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"56.21 MB","totalMemory":"120.00 MB","memoryUsage":"1.40%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"63.79 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.34 GB","freeSpace":"167.22 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"computerName":"LENOVO 20R7A018CD","osArch":"64位","systemUptime":"57分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.77 GB","totalMemory":"15.71 GB","memoryUsage":"81.29%","availableMemory":"2.94 GB"},"cpu":{"cpuUsage":"7.19%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1068, 1, NULL, '2026-06-05 08:25:36'),
	(86, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"62.61 MB","totalMemory":"120.00 MB","memoryUsage":"1.56%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"57.39 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.34 GB","freeSpace":"167.22 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"computerName":"LENOVO 20R7A018CD","osArch":"64位","systemUptime":"57分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.82 GB","totalMemory":"15.71 GB","memoryUsage":"81.61%","availableMemory":"2.89 GB"},"cpu":{"cpuUsage":"10.40%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1074, 1, NULL, '2026-06-05 08:26:06'),
	(87, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"69.58 MB","totalMemory":"108.00 MB","memoryUsage":"1.73%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"38.42 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.34 GB","freeSpace":"167.22 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"computerName":"LENOVO 20R7A018CD","osArch":"64位","systemUptime":"58分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.89 GB","totalMemory":"15.71 GB","memoryUsage":"82.07%","availableMemory":"2.82 GB"},"cpu":{"cpuUsage":"15.19%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1207, 1, NULL, '2026-06-05 08:26:36'),
	(88, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"35.20 MB","totalMemory":"100.00 MB","memoryUsage":"0.87%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"64.80 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.34 GB","freeSpace":"167.22 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"computerName":"LENOVO 20R7A018CD","osArch":"64位","systemUptime":"58分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.86 GB","totalMemory":"15.71 GB","memoryUsage":"81.83%","availableMemory":"2.85 GB"},"cpu":{"cpuUsage":"10.78%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1218, 1, NULL, '2026-06-05 08:27:07'),
	(89, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"41.26 MB","totalMemory":"100.00 MB","memoryUsage":"1.03%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"58.74 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.34 GB","freeSpace":"167.22 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"computerName":"LENOVO 20R7A018CD","osArch":"64位","systemUptime":"59分钟","osName":"Windows 11"},"memory":{"usedMemory":"13.13 GB","totalMemory":"15.71 GB","memoryUsage":"83.56%","availableMemory":"2.58 GB"},"cpu":{"cpuUsage":"20.76%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1103, 1, NULL, '2026-06-05 08:27:36'),
	(90, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"47.44 MB","totalMemory":"100.00 MB","memoryUsage":"1.18%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"52.56 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.34 GB","freeSpace":"167.22 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"computerName":"LENOVO 20R7A018CD","osArch":"64位","systemUptime":"59分钟","osName":"Windows 11"},"memory":{"usedMemory":"13.19 GB","totalMemory":"15.71 GB","memoryUsage":"83.95%","availableMemory":"2.52 GB"},"cpu":{"cpuUsage":"3.90%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1036, 1, NULL, '2026-06-05 08:28:06'),
	(91, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"53.42 MB","totalMemory":"100.00 MB","memoryUsage":"1.33%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"46.58 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.34 GB","freeSpace":"167.22 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"computerName":"LENOVO 20R7A018CD","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"13.04 GB","totalMemory":"15.71 GB","memoryUsage":"82.98%","availableMemory":"2.67 GB"},"cpu":{"cpuUsage":"10.16%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1137, 1, NULL, '2026-06-05 08:28:36'),
	(92, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"49.97 MB","totalMemory":"100.00 MB","memoryUsage":"1.24%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"50.03 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.35 GB","freeSpace":"167.21 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"projectStartTime":"2026-06-05 08:24:34","computerName":"LENOVO 20R7A018CD","projectUptime":"4分钟29秒","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.90 GB","totalMemory":"15.71 GB","memoryUsage":"82.11%","availableMemory":"2.81 GB"},"cpu":{"cpuUsage":"9.64%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1301, 1, NULL, '2026-06-05 08:29:05'),
	(93, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"53.16 MB","totalMemory":"100.00 MB","memoryUsage":"1.32%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"46.84 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.35 GB","freeSpace":"167.21 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"projectStartTime":"2026-06-05 08:24:34","computerName":"LENOVO 20R7A018CD","projectUptime":"4分钟33秒","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.91 GB","totalMemory":"15.71 GB","memoryUsage":"82.15%","availableMemory":"2.80 GB"},"cpu":{"cpuUsage":"4.24%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1048, 1, NULL, '2026-06-05 08:29:08'),
	(94, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"55.38 MB","totalMemory":"100.00 MB","memoryUsage":"1.38%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"44.62 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.35 GB","freeSpace":"167.21 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"projectStartTime":"2026-06-05 08:24:34","computerName":"LENOVO 20R7A018CD","projectUptime":"4分钟34秒","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.93 GB","totalMemory":"15.71 GB","memoryUsage":"82.27%","availableMemory":"2.79 GB"},"cpu":{"cpuUsage":"13.63%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1084, 1, NULL, '2026-06-05 08:29:09'),
	(95, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"58.56 MB","totalMemory":"100.00 MB","memoryUsage":"1.46%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"41.44 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.35 GB","freeSpace":"167.21 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"projectStartTime":"2026-06-05 08:24:34","computerName":"LENOVO 20R7A018CD","projectUptime":"4分钟39秒","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.92 GB","totalMemory":"15.71 GB","memoryUsage":"82.25%","availableMemory":"2.79 GB"},"cpu":{"cpuUsage":"9.39%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1075, 1, NULL, '2026-06-05 08:29:14'),
	(96, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"61.63 MB","totalMemory":"100.00 MB","memoryUsage":"1.53%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"38.37 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.35 GB","freeSpace":"167.21 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"projectStartTime":"2026-06-05 08:24:34","computerName":"LENOVO 20R7A018CD","projectUptime":"4分钟44秒","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.93 GB","totalMemory":"15.71 GB","memoryUsage":"82.31%","availableMemory":"2.78 GB"},"cpu":{"cpuUsage":"5.95%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1066, 1, NULL, '2026-06-05 08:29:19'),
	(97, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"64.76 MB","totalMemory":"100.00 MB","memoryUsage":"1.61%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"35.24 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.35 GB","freeSpace":"167.21 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"projectStartTime":"2026-06-05 08:24:34","computerName":"LENOVO 20R7A018CD","projectUptime":"4分钟49秒","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.93 GB","totalMemory":"15.71 GB","memoryUsage":"82.31%","availableMemory":"2.78 GB"},"cpu":{"cpuUsage":"7.87%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1064, 1, NULL, '2026-06-05 08:29:24'),
	(98, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"68.03 MB","totalMemory":"100.00 MB","memoryUsage":"1.69%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"31.97 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.35 GB","freeSpace":"167.21 GB","usage":"37.03%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"projectStartTime":"2026-06-05 08:24:34","computerName":"LENOVO 20R7A018CD","projectUptime":"4分钟54秒","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.96 GB","totalMemory":"15.71 GB","memoryUsage":"82.46%","availableMemory":"2.76 GB"},"cpu":{"cpuUsage":"4.44%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1048, 1, NULL, '2026-06-05 08:29:29'),
	(99, 2, 'admin', '超级管理员', 21, '系统监控', 4, '查看系统监控信息', '/api/monitor/system', 'GET', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":{"jvm":{"jvmVendor":"Oracle Corporation","usedMemory":"71.18 MB","totalMemory":"100.00 MB","memoryUsage":"1.77%","jvmVersion":"17","jvmName":"OpenJDK 64-Bit Server VM","freeMemory":"28.82 MB","maxMemory":"3.93 GB"},"disk":{"disks":[{"usedSpace":"98.35 GB","freeSpace":"167.21 GB","usage":"37.04%","name":"本地固定磁盘 (C:)","totalSpace":"265.56 GB","type":"NTFS","mount":"C:\\\\"},{"usedSpace":"82.91 GB","freeSpace":"17.72 GB","usage":"82.39%","name":"本地固定磁盘 (D:)","totalSpace":"100.63 GB","type":"NTFS","mount":"D:\\\\"},{"usedSpace":"90.21 GB","freeSpace":"9.81 GB","usage":"90.19%","name":"本地固定磁盘 (E:)","totalSpace":"100.03 GB","type":"NTFS","mount":"E:\\\\"}]},"system":{"projectStartTime":"2026-06-05 08:24:34","computerName":"LENOVO 20R7A018CD","projectUptime":"4分钟59秒","osArch":"64位","systemUptime":"1小时0分钟","osName":"Windows 11"},"memory":{"usedMemory":"12.93 GB","totalMemory":"15.71 GB","memoryUsage":"82.30%","availableMemory":"2.78 GB"},"cpu":{"cpuUsage":"4.63%","cpuCores":"8核","cpuLoad1":"N/A","cpuName":"Intel(R) Core(TM) i5-10210U CPU @ 1.60GHz","cpuPhysicalCores":"4核","cpuLoad5":"N/A","cpuLoad15":"N/A"}},"success":true}', 1046, 1, NULL, '2026-06-05 08:29:34'),
	(100, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["5"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 2, 1, NULL, '2026-06-05 12:34:24'),
	(101, 2, 'admin', '超级管理员', 21, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 12, 1, NULL, '2026-06-05 12:34:39'),
	(102, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["5"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 3, 1, NULL, '2026-06-05 12:40:22'),
	(103, 2, 'admin', '超级管理员', 21, '角色管理', 3, '批量删除角色', '/api/role/batch', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"批量删除角色成功","success":true}', 26, 1, NULL, '2026-06-05 12:43:47'),
	(104, 2, 'admin', '超级管理员', 21, '角色管理', 3, '批量删除角色', '/api/role/batch', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"批量删除角色成功","success":true}', 4, 1, NULL, '2026-06-05 12:43:53'),
	(105, 2, 'admin', '超级管理员', 21, '用户管理', 3, '删除用户', '/api/user/8', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除用户成功","success":true}', 18, 1, NULL, '2026-06-05 12:44:16'),
	(106, 2, 'admin', '超级管理员', 21, '角色管理', 3, '删除角色', '/api/role/5', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除角色成功","success":true}', 8, 1, NULL, '2026-06-05 12:44:21'),
	(107, 2, 'admin', '超级管理员', 21, '角色管理', 1, '新增角色', '/api/role', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增角色成功","success":true}', 11, 1, NULL, '2026-06-05 12:44:51'),
	(108, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["6"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 2, 1, NULL, '2026-06-05 12:44:58'),
	(109, 2, 'admin', '超级管理员', 21, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 17, 1, NULL, '2026-06-05 12:45:21'),
	(110, 2, 'admin', '超级管理员', 21, '用户管理', 3, '删除用户', '/api/user/9', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除用户成功","success":true}', 8, 1, NULL, '2026-06-05 12:50:45'),
	(111, 2, 'admin', '超级管理员', 21, '角色管理', 3, '删除角色', '/api/role/6', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除角色成功","success":true}', 18, 1, NULL, '2026-06-05 12:50:50'),
	(112, 2, 'admin', '超级管理员', 21, '角色管理', 1, '新增角色', '/api/role', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增角色成功","success":true}', 10, 1, NULL, '2026-06-05 12:51:00'),
	(113, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["7"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 17, 1, NULL, '2026-06-05 12:51:05'),
	(114, 2, 'admin', '超级管理员', 21, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 17, 1, NULL, '2026-06-05 12:52:24'),
	(115, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["7"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 16, 1, NULL, '2026-06-05 12:58:27'),
	(116, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["7"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 20, 1, NULL, '2026-06-05 13:02:53'),
	(117, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["7"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 10, 1, NULL, '2026-06-05 13:07:22'),
	(118, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["7"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 10, 1, NULL, '2026-06-05 13:08:09'),
	(119, 2, 'admin', '超级管理员', 21, '菜单权限', 1, '新增权限', '/api/permission', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增成功","success":true}', 7, 1, NULL, '2026-06-05 13:12:06'),
	(120, 2, 'admin', '超级管理员', 21, '菜单权限', 2, '更新权限', '/api/permission', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"更新成功","success":true}', 6, 1, NULL, '2026-06-05 13:12:22'),
	(121, 2, 'admin', '超级管理员', 21, '菜单权限', 3, '删除权限', '/api/permission/21', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除成功","success":true}', 7, 1, NULL, '2026-06-05 13:12:55'),
	(122, 2, 'admin', '超级管理员', 21, '菜单权限', 1, '新增权限', '/api/permission', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增成功","success":true}', 9, 1, NULL, '2026-06-05 13:18:39'),
	(123, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["7"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 16, 1, NULL, '2026-06-05 13:19:06'),
	(124, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["2"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 17, 1, NULL, '2026-06-05 13:26:32'),
	(125, 2, 'admin', '超级管理员', 21, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["2"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 12, 1, NULL, '2026-06-05 13:26:53'),
	(126, 2, 'admin', '超级管理员', 21, '角色管理', 3, '删除角色', '/api/role/7', 'DELETE', '127.0.0.1', NULL, '{"code":500,"message":"该角色正在被 1 个用户使用，请先解除用户关联后再删除","data":null,"success":false}', 13, 1, NULL, '2026-06-05 13:34:00'),
	(127, 2, 'admin', '超级管理员', 21, '用户管理', 3, '删除用户', '/api/user/10', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除用户成功","success":true}', 8, 1, NULL, '2026-06-05 13:34:07'),
	(128, 2, NULL, NULL, NULL, '用户管理', 3, '删除用户', '/api/user/2', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除用户成功","success":true}', 4, 1, NULL, '2026-06-05 13:34:11'),
	(129, 2, NULL, NULL, NULL, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增用户成功","success":true}', 13, 1, NULL, '2026-06-05 13:34:38'),
	(130, 2, NULL, NULL, NULL, '角色管理', 3, '删除角色', '/api/role/2', 'DELETE', '127.0.0.1', NULL, '{"code":500,"message":"超级管理员角色不允许删除，这是系统安全保护机制","data":null,"success":false}', 11, 1, NULL, '2026-06-05 13:35:36'),
	(131, 11, NULL, NULL, NULL, '用户管理', 3, '删除用户', '/api/user/11', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除用户成功","success":true}', 18, 1, NULL, '2026-06-05 13:37:51'),
	(132, 11, NULL, NULL, NULL, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增用户成功","success":true}', 23, 1, NULL, '2026-06-05 13:38:15'),
	(133, 11, NULL, NULL, NULL, '角色管理', 3, '删除角色', '/api/role/2', 'DELETE', '127.0.0.1', NULL, '{"code":500,"message":"超级管理员角色不允许删除，这是系统安全保护机制","data":null,"success":false}', 10, 1, NULL, '2026-06-05 13:38:47'),
	(134, 12, 'admin', '码动未来', 20, '角色管理', 3, '批量删除角色', '/api/role/batch', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"批量删除角色成功","success":true}', 31, 1, NULL, '2026-06-05 13:45:31'),
	(135, 12, 'admin', '码动未来', 20, '角色管理', 1, '新增角色', '/api/role', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增角色成功","success":true}', 16, 1, NULL, '2026-06-05 13:46:04'),
	(136, 12, 'admin', '码动未来', 20, '角色管理', 1, '新增角色', '/api/role', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增角色成功","success":true}', 7, 1, NULL, '2026-06-05 13:46:20'),
	(137, 12, 'admin', '码动未来', 20, '角色管理', 1, '新增角色', '/api/role', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增角色成功","success":true}', 8, 1, NULL, '2026-06-05 13:46:31'),
	(138, 12, 'admin', '码动未来', 20, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["10"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 25, 1, NULL, '2026-06-05 13:46:48'),
	(139, 12, 'admin', '码动未来', 20, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["9"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 12, 1, NULL, '2026-06-05 13:47:24'),
	(140, 12, 'admin', '码动未来', 20, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增用户成功","success":true}', 15, 1, NULL, '2026-06-05 13:48:00'),
	(141, 12, 'admin', '码动未来', 20, '用户管理', 2, '修改用户', '/api/user', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"修改用户成功","success":true}', 29, 1, NULL, '2026-06-05 13:48:17'),
	(142, 12, 'admin', '码动未来', 20, '用户管理', 3, '删除用户', '/api/user/13', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除用户成功","success":true}', 30, 1, NULL, '2026-06-05 13:59:36'),
	(143, 12, 'admin', '码动未来', 20, '角色管理', 3, '批量删除角色', '/api/role/batch', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"批量删除角色成功","success":true}', 45, 1, NULL, '2026-06-05 13:59:44'),
	(144, 12, 'admin', '码动未来', 20, '菜单权限', 2, '更新权限', '/api/permission', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"更新成功","success":true}', 16, 1, NULL, '2026-06-05 14:00:43'),
	(145, 12, 'admin', '码动未来', 20, '菜单权限', 2, '更新权限', '/api/permission', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"更新成功","success":true}', 6, 1, NULL, '2026-06-05 14:01:07'),
	(146, 12, 'admin', '码动未来', 20, '菜单权限', 2, '更新权限', '/api/permission', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"更新成功","success":true}', 5, 1, NULL, '2026-06-05 14:01:35'),
	(147, 12, 'admin', '码动未来', 20, '菜单权限', 2, '更新权限', '/api/permission', 'PUT', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"更新成功","success":true}', 6, 1, NULL, '2026-06-05 14:01:58'),
	(148, 12, 'admin', '码动未来', 20, '角色管理', 1, '新增角色', '/api/role', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增角色成功","success":true}', 15, 1, NULL, '2026-06-05 14:02:58'),
	(149, 12, 'admin', '码动未来', 20, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["11"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 25, 1, NULL, '2026-06-05 14:03:11'),
	(150, 12, 'admin', '码动未来', 20, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增用户成功","success":true}', 15, 1, NULL, '2026-06-05 14:03:34'),
	(151, 12, 'admin', '码动未来', 20, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["11"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 17, 1, NULL, '2026-06-05 14:04:22'),
	(152, 12, 'admin', '码动未来', 20, '菜单权限', 1, '新增权限', '/api/permission', 'POST', '127.0.0.1', NULL, '{"code":500,"message":"新增失败: \\r\\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'sys:user:search\' for key \'perm_key\'\\r\\n### The error may exist in file [D:\\\\javacode\\\\eutmp\\\\target\\\\classes\\\\mapper\\\\SysPermissionMapper.xml]\\r\\n### The error may involve com.eutmp.app.mapper.SysPermissionMapper.insert-Inline\\r\\n### The error occurred while setting parameters\\r\\n### SQL: INSERT INTO sys_permission (             parent_id, perm_name, perm_key, perm_type, path, icon, sort, status, create_time, update_time         ) VALUES (             ?, ?, ?, ?, ?, ?, ?, ?, ?, ?         )\\r\\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'sys:user:search\' for key \'perm_key\'\\n; Duplicate entry \'sys:user:search\' for key \'perm_key\'","data":null,"success":false}', 177, 1, NULL, '2026-06-05 14:05:42'),
	(153, 12, 'admin', '码动未来', 20, '角色管理', 3, '删除角色', '/api/role/11', 'DELETE', '127.0.0.1', NULL, '{"code":500,"message":"该角色正在被 1 个用户使用，请先解除用户关联后再删除","data":null,"success":false}', 12, 1, NULL, '2026-06-05 14:13:58'),
	(154, 12, 'admin', '码动未来', 20, '用户管理', 3, '删除用户', '/api/user/14', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除用户成功","success":true}', 9, 1, NULL, '2026-06-05 14:14:02'),
	(155, 12, 'admin', '码动未来', 20, '角色管理', 3, '删除角色', '/api/role/11', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除角色成功","success":true}', 12, 1, NULL, '2026-06-05 14:14:26'),
	(156, 12, 'admin', '码动未来', 20, '角色管理', 1, '新增角色', '/api/role', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增角色成功","success":true}', 290, 1, NULL, '2026-06-05 14:48:20'),
	(157, 12, 'admin', '码动未来', 20, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["12"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 22, 1, NULL, '2026-06-05 14:49:06'),
	(158, 12, 'admin', '码动未来', 20, '用户管理', 1, '新增用户', '/api/user', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增用户成功","success":true}', 21, 1, NULL, '2026-06-05 14:50:13'),
	(159, 12, 'admin', '码动未来', 20, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["12"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 12, 1, NULL, '2026-06-05 14:51:06'),
	(160, 12, 'admin', '码动未来', 20, '菜单权限', 1, '新增权限', '/api/permission', 'POST', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"新增成功","success":true}', 9, 1, NULL, '2026-06-05 14:54:00'),
	(161, 12, 'admin', '码动未来', 20, '角色管理', 2, '分配角色权限', '/api/role/permission/assign', 'POST', '127.0.0.1', '{"roleId":["12"]}', '{"code":200,"message":"操作成功","data":"分配权限成功","success":true}', 13, 1, NULL, '2026-06-05 14:55:00'),
	(162, 12, 'admin', '码动未来', 20, '菜单权限', 3, '删除权限', '/api/permission/24', 'DELETE', '127.0.0.1', NULL, '{"code":200,"message":"操作成功","data":"删除成功","success":true}', 10, 1, NULL, '2026-06-05 14:55:49');

-- 导出  表 eutmp.sys_permission 结构
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父权限ID(0顶级菜单)',
  `perm_name` varchar(50) NOT NULL COMMENT '权限名称',
  `perm_key` varchar(100) NOT NULL COMMENT '权限标识符(sys:user:list)',
  `perm_type` tinyint(4) NOT NULL COMMENT '1菜单 2按钮 3接口',
  `path` varchar(255) DEFAULT '' COMMENT '前端路由地址',
  `icon` varchar(255) DEFAULT '' COMMENT '菜单图标',
  `sort` int(11) DEFAULT 0 COMMENT '排序号',
  `status` tinyint(4) DEFAULT 1,
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `perm_key` (`perm_key`),
  KEY `idx_parent` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='权限资源表';

-- 正在导出表  eutmp.sys_permission 的数据：~101 rows (大约)
INSERT INTO `sys_permission` (`id`, `parent_id`, `perm_name`, `perm_key`, `perm_type`, `path`, `icon`, `sort`, `status`, `create_time`, `update_time`) VALUES
	(1, 0, '系统管理', 'system:manage', 1, '/system', 'system', 2, 1, '2026-06-03 16:18:55', '2026-06-05 16:15:06'),
	(2, 1, '用户管理', 'sys:user:list', 1, '/system/user', 'user', 1, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(3, 2, '用户新增', 'sys:user:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-03 16:18:55', '2026-06-05 14:00:43'),
	(4, 2, '用户编辑', 'sys:user:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-03 16:18:55', '2026-06-05 14:01:07'),
	(5, 2, '用户删除', 'sys:user:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-03 16:18:55', '2026-06-05 14:01:35'),
	(6, 2, '用户导出', 'sys:user:export', 2, '', 'fas fa-newspaper', 4, 1, '2026-06-03 16:18:55', '2026-06-05 14:01:58'),
	(7, 1, '角色管理', 'sys:role:list', 1, '/system/role', 'role', 2, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(8, 7, '角色新增', 'sys:role:add', 2, '', '', 1, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(9, 7, '角色编辑', 'sys:role:edit', 2, '', '', 2, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(10, 7, 'SQL监控', 'sys:role:remove', 2, '/system/druid', 'fas fa-database', 3, 1, '2026-06-03 16:18:55', '2026-06-04 15:16:28'),
	(11, 1, '部门管理', 'sys:dept:list', 1, '/system/dept', 'dept', 3, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(12, 11, '部门新增', 'sys:dept:add', 2, '', '', 1, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(13, 11, '部门编辑', 'sys:dept:edit', 2, '', '', 2, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(14, 11, '部门删除', 'sys:dept:remove', 2, '', '', 3, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(15, 1, '菜单权限', 'sys:perm:list', 1, '/system/menu', 'menu', 4, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(16, 15, '权限新增', 'sys:perm:add', 2, '', '', 1, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(17, 1, '操作日志', 'sys:log:list', 1, '/system/log', 'log', 5, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(18, 17, '日志删除', 'sys:log:remove', 2, '', '', 1, 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(19, 1, 'SQL监控', 'system:druid:view', 1, '/system/druid', 'fas fa-database', 6, 1, '2026-06-04 15:20:21', '2026-06-04 15:20:21'),
	(20, 1, '系统监控', 'system:monitor', 1, '/system/monitor', 'fas fa-chart-line', 6, 1, '2026-06-05 08:23:25', '2026-06-05 08:23:25'),
	(22, 2, '查询', 'sys:user:search', 2, '', 'fas fa-search', 0, 1, NULL, NULL),
	(60, 0, '数据管理', 'data:manage', 1, '/data', 'fas fa-database', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:15:06'),
	(61, 60, '学院管理', 'data:college:list', 1, '/data/college', 'fas fa-university', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:13:55'),
	(62, 61, '学院新增', 'data:college:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(63, 61, '学院编辑', 'data:college:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(64, 61, '学院删除', 'data:college:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(65, 61, '学院查询', 'data:college:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(66, 61, '学院导出', 'data:college:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(67, 60, '专业管理', 'data:major:list', 1, '/data/major', 'fas fa-book-open', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:13:55'),
	(68, 67, '专业新增', 'data:major:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(69, 67, '专业编辑', 'data:major:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(70, 67, '专业删除', 'data:major:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(71, 67, '专业查询', 'data:major:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(72, 67, '专业导出', 'data:major:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(73, 60, '班级管理', 'data:class:list', 1, '/data/class', 'fas fa-chalkboard', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:13:55'),
	(74, 73, '班级新增', 'data:class:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(75, 73, '班级编辑', 'data:class:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(76, 73, '班级删除', 'data:class:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(77, 73, '班级查询', 'data:class:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(78, 73, '班级导出', 'data:class:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(79, 60, '课程管理', 'data:course:list', 1, '/data/course', 'fas fa-book', 4, 1, '2026-06-05 16:03:37', '2026-06-05 16:13:55'),
	(80, 79, '课程新增', 'data:course:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(81, 79, '课程编辑', 'data:course:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(82, 79, '课程删除', 'data:course:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(83, 79, '课程查询', 'data:course:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(84, 79, '课程导出', 'data:course:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(85, 60, '学生管理', 'data:student:list', 1, '/data/student', 'fas fa-user-graduate', 5, 1, '2026-06-05 16:03:37', '2026-06-05 16:13:55'),
	(86, 85, '学生新增', 'data:student:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(87, 85, '学生编辑', 'data:student:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(88, 85, '学生删除', 'data:student:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(89, 85, '学生查询', 'data:student:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(90, 85, '学生导出', 'data:student:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(91, 60, '成绩管理', 'data:score:list', 1, '/data/score', 'fas fa-chart-line', 6, 1, '2026-06-05 16:03:37', '2026-06-05 16:13:55'),
	(92, 91, '成绩新增', 'data:score:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(93, 91, '成绩编辑', 'data:score:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(94, 91, '成绩删除', 'data:score:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(95, 91, '成绩查询', 'data:score:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(96, 91, '成绩导出', 'data:score:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:03:37', '2026-06-05 16:03:37'),
	(97, 60, '实训项目', 'data:project:list', 1, '/data/project', 'fas fa-project-diagram', 7, 1, '2026-06-05 16:06:45', '2026-06-05 16:13:55'),
	(98, 97, '项目新增', 'data:project:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(99, 97, '项目编辑', 'data:project:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(100, 97, '项目删除', 'data:project:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(101, 97, '项目查询', 'data:project:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(102, 97, '项目导出', 'data:project:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(103, 60, '实训开班', 'data:plan:list', 1, '/data/plan', 'fas fa-calendar-alt', 8, 1, '2026-06-05 16:06:45', '2026-06-05 16:13:55'),
	(104, 103, '开班新增', 'data:plan:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(105, 103, '开班编辑', 'data:plan:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(106, 103, '开班删除', 'data:plan:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(107, 103, '开班查询', 'data:plan:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(108, 103, '开班导出', 'data:plan:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(109, 60, '报名管理', 'data:enroll:list', 1, '/data/enroll', 'fas fa-clipboard-list', 9, 1, '2026-06-05 16:06:45', '2026-06-05 16:13:55'),
	(110, 109, '报名新增', 'data:enroll:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(111, 109, '报名审核', 'data:enroll:audit', 2, '', 'fas fa-check-circle', 2, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(112, 109, '报名删除', 'data:enroll:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(113, 109, '报名查询', 'data:enroll:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(114, 109, '报名导出', 'data:enroll:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(115, 109, '报名驳回', 'data:enroll:reject', 2, '', 'fas fa-times-circle', 6, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(116, 60, '考勤管理', 'data:attendance:list', 1, '/data/attendance', 'fas fa-clock', 10, 1, '2026-06-05 16:06:45', '2026-06-05 16:13:55'),
	(117, 116, '考勤登记', 'data:attendance:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(118, 116, '考勤编辑', 'data:attendance:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(119, 116, '考勤删除', 'data:attendance:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(120, 116, '考勤查询', 'data:attendance:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(121, 116, '考勤导出', 'data:attendance:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(122, 60, '耗材管理', 'data:material:list', 1, '/data/material', 'fas fa-box', 11, 1, '2026-06-05 16:06:45', '2026-06-05 16:13:55'),
	(123, 122, '耗材新增', 'data:material:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(124, 122, '耗材编辑', 'data:material:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(125, 122, '耗材删除', 'data:material:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(126, 122, '耗材查询', 'data:material:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(127, 122, '耗材导出', 'data:material:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(128, 60, '耗材领用', 'data:materialUse:list', 1, '/data/materialUse', 'fas fa-dolly', 12, 1, '2026-06-05 16:06:45', '2026-06-05 16:13:55'),
	(129, 128, '领用登记', 'data:materialUse:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(130, 128, '领用编辑', 'data:materialUse:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(131, 128, '领用删除', 'data:materialUse:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(132, 128, '领用查询', 'data:materialUse:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(133, 128, '领用导出', 'data:materialUse:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(134, 60, '实训成绩', 'data:trainScore:list', 1, '/data/trainScore', 'fas fa-award', 13, 1, '2026-06-05 16:06:45', '2026-06-05 16:13:55'),
	(135, 134, '成绩录入', 'data:trainScore:add', 2, '', 'fas fa-plus', 1, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(136, 134, '成绩编辑', 'data:trainScore:edit', 2, '', 'fas fa-edit', 2, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(137, 134, '成绩删除', 'data:trainScore:remove', 2, '', 'fas fa-trash', 3, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(138, 134, '成绩查询', 'data:trainScore:search', 2, '', 'fas fa-search', 4, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45'),
	(139, 134, '成绩导出', 'data:trainScore:export', 2, '', 'fas fa-file-export', 5, 1, '2026-06-05 16:06:45', '2026-06-05 16:06:45');

-- 导出  表 eutmp.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_code` varchar(50) NOT NULL COMMENT '角色标识(admin/user)',
  `remark` varchar(255) DEFAULT '' COMMENT '角色备注',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0禁用1启用',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code` (`role_code`),
  KEY `idx_rolecode` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色表';

-- 正在导出表  eutmp.sys_role 的数据：~2 rows (大约)
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `remark`, `status`, `create_time`, `update_time`) VALUES
	(2, '超级管理员', 'admin', '系统超级管理员，拥有所有权限', 1, '2026-06-03 16:18:55', '2026-06-03 16:18:55'),
	(12, '教务管理员', 'teacher_manager', '', 1, '2026-06-05 14:48:20', '2026-06-05 14:48:20');

-- 导出  表 eutmp.sys_role_dept 结构
CREATE TABLE IF NOT EXISTS `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '可访问部门ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_dept` (`role_id`,`dept_id`),
  KEY `dept_id` (`dept_id`),
  CONSTRAINT `sys_role_dept_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_role_dept_ibfk_2` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色-数据权限(部门关联表)';

-- 正在导出表  eutmp.sys_role_dept 的数据：~0 rows (大约)

-- 导出  表 eutmp.sys_role_permission 结构
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `perm_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_perm` (`role_id`,`perm_id`),
  KEY `perm_id` (`perm_id`),
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`perm_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色权限关联表';

-- 正在导出表  eutmp.sys_role_permission 的数据：~108 rows (大约)
INSERT INTO `sys_role_permission` (`id`, `role_id`, `perm_id`) VALUES
	(95, 2, 1),
	(96, 2, 2),
	(104, 2, 3),
	(105, 2, 4),
	(106, 2, 5),
	(107, 2, 6),
	(97, 2, 7),
	(108, 2, 8),
	(109, 2, 9),
	(110, 2, 10),
	(98, 2, 11),
	(111, 2, 12),
	(112, 2, 13),
	(113, 2, 14),
	(99, 2, 15),
	(114, 2, 16),
	(100, 2, 17),
	(115, 2, 18),
	(101, 2, 19),
	(102, 2, 20),
	(103, 2, 22),
	(350, 2, 60),
	(351, 2, 61),
	(352, 2, 62),
	(353, 2, 63),
	(354, 2, 64),
	(355, 2, 65),
	(356, 2, 66),
	(357, 2, 67),
	(358, 2, 68),
	(359, 2, 69),
	(360, 2, 70),
	(361, 2, 71),
	(362, 2, 72),
	(363, 2, 73),
	(364, 2, 74),
	(365, 2, 75),
	(366, 2, 76),
	(367, 2, 77),
	(368, 2, 78),
	(369, 2, 79),
	(370, 2, 80),
	(371, 2, 81),
	(372, 2, 82),
	(373, 2, 83),
	(374, 2, 84),
	(375, 2, 85),
	(376, 2, 86),
	(377, 2, 87),
	(378, 2, 88),
	(379, 2, 89),
	(380, 2, 90),
	(381, 2, 91),
	(382, 2, 92),
	(383, 2, 93),
	(384, 2, 94),
	(385, 2, 95),
	(386, 2, 96),
	(387, 2, 97),
	(388, 2, 98),
	(389, 2, 99),
	(390, 2, 100),
	(391, 2, 101),
	(392, 2, 102),
	(393, 2, 103),
	(394, 2, 104),
	(395, 2, 105),
	(396, 2, 106),
	(397, 2, 107),
	(398, 2, 108),
	(399, 2, 109),
	(400, 2, 110),
	(401, 2, 111),
	(402, 2, 112),
	(403, 2, 113),
	(404, 2, 114),
	(405, 2, 115),
	(406, 2, 116),
	(407, 2, 117),
	(408, 2, 118),
	(409, 2, 119),
	(410, 2, 120),
	(411, 2, 121),
	(412, 2, 122),
	(413, 2, 123),
	(414, 2, 124),
	(415, 2, 125),
	(416, 2, 126),
	(417, 2, 127),
	(418, 2, 128),
	(419, 2, 129),
	(420, 2, 130),
	(421, 2, 131),
	(422, 2, 132),
	(423, 2, 133),
	(424, 2, 134),
	(425, 2, 135),
	(426, 2, 136),
	(427, 2, 137),
	(428, 2, 138),
	(429, 2, 139),
	(146, 12, 1),
	(147, 12, 2),
	(148, 12, 3),
	(149, 12, 4),
	(150, 12, 5),
	(151, 12, 6),
	(152, 12, 22),
	(270, 12, 60),
	(271, 12, 61),
	(272, 12, 62),
	(273, 12, 63),
	(274, 12, 64),
	(275, 12, 65),
	(276, 12, 66),
	(277, 12, 67),
	(278, 12, 68),
	(279, 12, 69),
	(280, 12, 70),
	(281, 12, 71),
	(282, 12, 72),
	(283, 12, 73),
	(284, 12, 74),
	(285, 12, 75),
	(286, 12, 76),
	(287, 12, 77),
	(288, 12, 78),
	(289, 12, 79),
	(290, 12, 80),
	(291, 12, 81),
	(292, 12, 82),
	(293, 12, 83),
	(294, 12, 84),
	(295, 12, 85),
	(296, 12, 86),
	(297, 12, 87),
	(298, 12, 88),
	(299, 12, 89),
	(300, 12, 90),
	(301, 12, 91),
	(302, 12, 92),
	(303, 12, 93),
	(304, 12, 94),
	(305, 12, 95),
	(306, 12, 96),
	(307, 12, 97),
	(308, 12, 98),
	(309, 12, 99),
	(310, 12, 100),
	(311, 12, 101),
	(312, 12, 102),
	(313, 12, 103),
	(314, 12, 104),
	(315, 12, 105),
	(316, 12, 106),
	(317, 12, 107),
	(318, 12, 108),
	(319, 12, 109),
	(320, 12, 110),
	(321, 12, 111),
	(322, 12, 112),
	(323, 12, 113),
	(324, 12, 114),
	(325, 12, 115),
	(326, 12, 116),
	(327, 12, 117),
	(328, 12, 118),
	(329, 12, 119),
	(330, 12, 120),
	(331, 12, 121),
	(332, 12, 122),
	(333, 12, 123),
	(334, 12, 124),
	(335, 12, 125),
	(336, 12, 126),
	(337, 12, 127),
	(338, 12, 128),
	(339, 12, 129),
	(340, 12, 130),
	(341, 12, 131),
	(342, 12, 132),
	(343, 12, 133),
	(344, 12, 134),
	(345, 12, 135),
	(346, 12, 136),
	(347, 12, 137),
	(348, 12, 138),
	(349, 12, 139);

-- 导出  表 eutmp.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '登录账号',
  `password` varchar(100) NOT NULL COMMENT '加密密码(BCrypt)',
  `nick_name` varchar(50) NOT NULL COMMENT '用户昵称',
  `phone` varchar(20) DEFAULT '' COMMENT '手机号',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1正常',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_username` (`username`),
  KEY `idx_dept` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户表';

-- 正在导出表  eutmp.sys_user 的数据：~2 rows (大约)
INSERT INTO `sys_user` (`id`, `username`, `password`, `nick_name`, `phone`, `email`, `dept_id`, `status`, `create_time`, `update_time`) VALUES
	(12, 'admin', '123456', '码动未来', '', '', 20, 1, '2026-06-05 13:38:15', '2026-06-05 13:38:15'),
	(15, '韩梅梅', '123456', '韩梅梅', '', '', 20, 1, '2026-06-05 14:50:13', '2026-06-05 14:50:13');

-- 导出  表 eutmp.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色关联表';

-- 正在导出表  eutmp.sys_user_role 的数据：~2 rows (大约)
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(16, 12, 2),
	(19, 15, 12);

-- 导出  表 eutmp.train_attendance 结构
CREATE TABLE IF NOT EXISTS `train_attendance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '考勤ID',
  `plan_id` bigint(20) NOT NULL COMMENT '实训开班ID',
  `stu_id` bigint(20) NOT NULL COMMENT '学生ID',
  `attend_date` date NOT NULL COMMENT '考勤日期',
  `sign_time` datetime DEFAULT NULL COMMENT '实际签到时间',
  `sign_status` tinyint(4) NOT NULL COMMENT '1正常出勤 2迟到 3早退 4缺勤 5请假',
  `leave_remark` varchar(300) DEFAULT '' COMMENT '请假事由',
  `create_user` bigint(20) NOT NULL COMMENT '登记教师ID',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_plan_stu_date` (`plan_id`,`stu_id`,`attend_date`),
  KEY `idx_plan` (`plan_id`),
  KEY `idx_stu` (`stu_id`),
  KEY `idx_date` (`attend_date`),
  KEY `fk_attend_user` (`create_user`),
  CONSTRAINT `fk_attend_plan` FOREIGN KEY (`plan_id`) REFERENCES `train_plan` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_attend_stu` FOREIGN KEY (`stu_id`) REFERENCES `student` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_attend_user` FOREIGN KEY (`create_user`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='实训日常考勤表';

-- 正在导出表  eutmp.train_attendance 的数据：~0 rows (大约)

-- 导出  表 eutmp.train_enroll 结构
CREATE TABLE IF NOT EXISTS `train_enroll` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报名主键ID',
  `plan_id` bigint(20) NOT NULL COMMENT '实训开班ID(train_plan.id)',
  `stu_id` bigint(20) NOT NULL COMMENT '学生ID student.id',
  `enroll_time` datetime DEFAULT current_timestamp() COMMENT '报名时间',
  `audit_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '审核状态:1待审核 2审核通过 3驳回',
  `audit_user` bigint(20) DEFAULT NULL COMMENT '审核人ID(sys_user.id)',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(255) DEFAULT '' COMMENT '审核备注',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_plan_stu` (`plan_id`,`stu_id`),
  KEY `idx_plan` (`plan_id`),
  KEY `idx_stu` (`stu_id`),
  KEY `idx_audit` (`audit_user`),
  CONSTRAINT `fk_enroll_audit` FOREIGN KEY (`audit_user`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_enroll_plan` FOREIGN KEY (`plan_id`) REFERENCES `train_plan` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_enroll_stu` FOREIGN KEY (`stu_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='实训学生报名表';

-- 正在导出表  eutmp.train_enroll 的数据：~0 rows (大约)

-- 导出  表 eutmp.train_material 结构
CREATE TABLE IF NOT EXISTS `train_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '耗材ID',
  `material_name` varchar(100) NOT NULL COMMENT '耗材名称',
  `spec` varchar(100) DEFAULT '' COMMENT '规格型号',
  `unit` varchar(20) NOT NULL COMMENT '计量单位(个/套/米)',
  `price` decimal(10,2) DEFAULT 0.00 COMMENT '采购单价',
  `stock_num` int(11) DEFAULT 0 COMMENT '当前库存数量',
  `warn_num` int(11) DEFAULT 10 COMMENT '库存预警值',
  `remark` varchar(300) DEFAULT '' COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0停用 1正常',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_name` (`material_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='实训耗材档案';

-- 正在导出表  eutmp.train_material 的数据：~0 rows (大约)

-- 导出  表 eutmp.train_material_use 结构
CREATE TABLE IF NOT EXISTS `train_material_use` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '领用记录ID',
  `plan_id` bigint(20) NOT NULL COMMENT '所属实训开班ID',
  `material_id` bigint(20) NOT NULL COMMENT '耗材ID',
  `use_num` int(11) NOT NULL COMMENT '领用数量',
  `use_user` bigint(20) NOT NULL COMMENT '领用教师ID',
  `use_date` date NOT NULL COMMENT '领用日期',
  `use_remark` varchar(300) DEFAULT '' COMMENT '使用说明',
  `create_time` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_plan` (`plan_id`),
  KEY `idx_material` (`material_id`),
  KEY `fk_use_user` (`use_user`),
  CONSTRAINT `fk_use_material` FOREIGN KEY (`material_id`) REFERENCES `train_material` (`id`),
  CONSTRAINT `fk_use_plan` FOREIGN KEY (`plan_id`) REFERENCES `train_plan` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_use_user` FOREIGN KEY (`use_user`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='实训耗材领用明细';

-- 正在导出表  eutmp.train_material_use 的数据：~0 rows (大约)

-- 导出  表 eutmp.train_plan 结构
CREATE TABLE IF NOT EXISTS `train_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实训开班ID',
  `project_id` bigint(20) NOT NULL COMMENT '关联实训项目ID(train_project.id)',
  `plan_name` varchar(100) NOT NULL COMMENT '实训开班名称',
  `term_year` varchar(20) NOT NULL COMMENT '学年学期(2025-2026上)',
  `start_date` date NOT NULL COMMENT '实训开始日期',
  `end_date` date NOT NULL COMMENT '实训结束日期',
  `train_address` varchar(200) DEFAULT '' COMMENT '实训地点(实训室/企业地址)',
  `teacher_id` bigint(20) NOT NULL COMMENT '主讲指导教师(sys_user.id)',
  `second_teacher_id` bigint(20) DEFAULT NULL COMMENT '辅助指导教师ID',
  `max_student` int(11) DEFAULT 50 COMMENT '最大容纳学生人数',
  `enroll_num` int(11) DEFAULT 0 COMMENT '已报名人数',
  `enroll_start` date DEFAULT NULL COMMENT '报名开始时间',
  `enroll_end` date DEFAULT NULL COMMENT '报名截止时间',
  `plan_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态:1待报名 2报名中 3进行中 4已结束 5取消',
  `remark` varchar(500) DEFAULT '' COMMENT '备注说明',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_project` (`project_id`),
  KEY `idx_teacher` (`teacher_id`),
  KEY `idx_term` (`term_year`),
  KEY `fk_plan_second_tea` (`second_teacher_id`),
  CONSTRAINT `fk_plan_project` FOREIGN KEY (`project_id`) REFERENCES `train_project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_plan_second_tea` FOREIGN KEY (`second_teacher_id`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_plan_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='实训开班计划表';

-- 正在导出表  eutmp.train_plan 的数据：~0 rows (大约)

-- 导出  表 eutmp.train_project 结构
CREATE TABLE IF NOT EXISTS `train_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实训项目ID',
  `project_name` varchar(120) NOT NULL COMMENT '实训项目名称',
  `project_code` varchar(40) NOT NULL COMMENT '项目编号',
  `project_type` tinyint(4) NOT NULL COMMENT '项目类型:1校内实训 2校外顶岗 3竞赛实训 4校企合作实训',
  `train_cycle` int(11) NOT NULL COMMENT '实训周期(天)',
  `credit` decimal(3,1) DEFAULT 0.0 COMMENT '实训学分',
  `cost` decimal(10,2) DEFAULT 0.00 COMMENT '项目人均耗材费用',
  `content` text DEFAULT NULL COMMENT '实训内容简介',
  `target` text DEFAULT NULL COMMENT '实训培养目标',
  `limit_major` varchar(500) DEFAULT '' COMMENT '限定适用专业ID，多个逗号分隔',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0停用 1启用',
  `create_user` bigint(20) NOT NULL COMMENT '创建人(sys_user.id)',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_project_code` (`project_code`),
  KEY `idx_create_user` (`create_user`),
  CONSTRAINT `fk_project_user` FOREIGN KEY (`create_user`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='实训项目资源库';

-- 正在导出表  eutmp.train_project 的数据：~0 rows (大约)

-- 导出  表 eutmp.train_score 结构
CREATE TABLE IF NOT EXISTS `train_score` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `plan_id` bigint(20) NOT NULL COMMENT '实训开班ID',
  `stu_id` bigint(20) NOT NULL COMMENT '学生ID',
  `usual_score` decimal(5,1) DEFAULT 0.0 COMMENT '平时成绩(满分50)',
  `report_score` decimal(5,1) DEFAULT 0.0 COMMENT '实训报告成绩(满分30)',
  `defense_score` decimal(5,1) DEFAULT 0.0 COMMENT '答辩考核成绩(满分20)',
  `total_score` decimal(5,1) DEFAULT 0.0 COMMENT '综合总分',
  `grade` varchar(10) DEFAULT '' COMMENT '等级:优秀/良好/中等/及格/不及格',
  `score_remark` varchar(500) DEFAULT '' COMMENT '评语',
  `score_teacher` bigint(20) NOT NULL COMMENT '打分教师ID',
  `score_time` datetime DEFAULT current_timestamp() COMMENT '打分时间',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_plan_stu_score` (`plan_id`,`stu_id`),
  KEY `idx_plan` (`plan_id`),
  KEY `idx_stu` (`stu_id`),
  KEY `fk_score_teacher` (`score_teacher`),
  CONSTRAINT `fk_score_plan` FOREIGN KEY (`plan_id`) REFERENCES `train_plan` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_score_stu` FOREIGN KEY (`stu_id`) REFERENCES `student` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_score_teacher` FOREIGN KEY (`score_teacher`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='实训综合成绩表';

-- 正在导出表  eutmp.train_score 的数据：~0 rows (大约)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
