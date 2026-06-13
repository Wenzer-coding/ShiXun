@echo off
chcp 65001 >nul
echo 正在生成剩余的Mapper XML文件...

echo 1. MajorMapper.xml...
echo ^<?xml version="1.0" encoding="UTF-8" ?^> > ..\src\main\resources\mapper\MajorMapper.xml
echo ^<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo ^<mapper namespace="com.eutmp.app.mapper.MajorMapper"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<resultMap id="BaseResultMap" type="com.eutmp.app.bean.Major"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<id column="id" property="id" jdbcType="BIGINT"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="college_id" property="collegeId" jdbcType="BIGINT"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="major_name" property="majorName" jdbcType="VARCHAR"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="major_code" property="majorCode" jdbcType="VARCHAR"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="edu_level" property="eduLevel" jdbcType="TINYINT"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="study_year" property="studyYear" jdbcType="INTEGER"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="sort" property="sort" jdbcType="INTEGER"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="status" property="status" jdbcType="TINYINT"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="create_time" property="createTime" jdbcType="TIMESTAMP"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="update_time" property="updateTime" jdbcType="TIMESTAMP"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<result column="college_name" property="collegeName" jdbcType="VARCHAR"/^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</resultMap^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<sql id="Base_Column_List"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         m.id, m.college_id, m.major_name, m.major_code, m.edu_level, m.study_year, m.sort, m.status, m.create_time, m.update_time, d.dept_name as college_name >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</sql^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<select id="selectById" resultMap="BaseResultMap"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         SELECT ^<include refid="Base_Column_List"/^> FROM edu_major m LEFT JOIN sys_dept d ON m.college_id = d.id WHERE m.id = #{id} >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</select^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<select id="selectAll" resultMap="BaseResultMap"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         SELECT ^<include refid="Base_Column_List"/^> FROM edu_major m LEFT JOIN sys_dept d ON m.college_id = d.id >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<where^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="majorName != null and majorName != ''"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo                 AND m.major_name LIKE CONCAT('%%', #{majorName}, '%%') >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="collegeId != null"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo                 AND m.college_id = #{collegeId} >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="status != null"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo                 AND m.status = #{status} >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^</where^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ORDER BY m.sort ASC, m.create_time DESC >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</select^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<insert id="insert" parameterType="com.eutmp.app.bean.Major" useGeneratedKeys="true" keyProperty="id"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         INSERT INTO edu_major (college_id, major_name, major_code, edu_level, study_year, sort, status, create_time, update_time) >> ..\src\main\resources\mapper\MajorMapper.xml
echo         VALUES (#{collegeId}, #{majorName}, #{majorCode}, #{eduLevel}, #{studyYear}, #{sort}, #{status}, #{createTime}, #{updateTime}) >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</insert^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<update id="update" parameterType="com.eutmp.app.bean.Major"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         UPDATE edu_major >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<set^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="collegeId != null"^>college_id = #{collegeId},^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="majorName != null"^>major_name = #{majorName},^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="majorCode != null"^>major_code = #{majorCode},^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="eduLevel != null"^>edu_level = #{eduLevel},^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="studyYear != null"^>study_year = #{studyYear},^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="sort != null"^>sort = #{sort},^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="status != null"^>status = #{status},^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             ^<if test="updateTime != null"^>update_time = #{updateTime},^</if^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^</set^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         WHERE id = #{id} >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</update^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<delete id="deleteById"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         DELETE FROM edu_major WHERE id = #{id} >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</delete^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<delete id="deleteByIds"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         DELETE FROM edu_major WHERE id IN >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^<foreach item="id" collection="ids" open="(" separator="," close=")"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo             #{id} >> ..\src\main\resources\mapper\MajorMapper.xml
echo         ^</foreach^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</delete^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^<select id="checkMajorCodeUnique" resultMap="BaseResultMap"^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo         SELECT ^<include refid="Base_Column_List"/^> FROM edu_major m LEFT JOIN sys_dept d ON m.college_id = d.id WHERE m.major_code = #{majorCode} LIMIT 1 >> ..\src\main\resources\mapper\MajorMapper.xml
echo     ^</select^> >> ..\src\main\resources\mapper\MajorMapper.xml
echo. >> ..\src\main\resources\mapper\MajorMapper.xml
echo ^</mapper^> >> ..\src\main\resources\mapper\MajorMapper.xml

echo 完成！
pause
