@echo off
chcp 65001 >nul
echo ============================================
echo   高校实训分析平台 - 一键启动脚本
echo ============================================
echo.

:: 1. 启动MySQL服务
echo [1/3] 启动 MySQL 服务...
net start MySQL
if %errorlevel% neq 0 (
    echo MySQL 启动失败，请检查服务是否安装！
    pause
    exit /b 1
)
echo MySQL 已启动。
echo.

:: 2. 导入数据库
echo [2/3] 导入数据库...
"D:\Code\mysql-9.7.0-winx64\mysql-9.7.0-winx64\bin\mysql.exe" -u wyb -p123456 < "%~dp0实训.sql"
if %errorlevel% neq 0 (
    echo 数据库导入失败，尝试用 root 导入...
    "D:\Code\mysql-9.7.0-winx64\mysql-9.7.0-winx64\bin\mysql.exe" -u root < "%~dp0实训.sql"
)
echo 数据库导入完成。
echo.

:: 3. 启动Spring Boot项目
echo [3/3] 启动项目...
echo.
echo ============================================
echo   签到二维码使用说明：
echo.
:: 获取本机局域网IP
for /f "tokens=2 delims=:" %%a in ('ipconfig ^| findstr /i "IPv4"') do set IP=%%a
set IP=%IP: =%
if defined IP (
    echo   请确保手机和电脑连接同一个WiFi
    echo   二维码签到地址: http://%IP%:8080/attendance/signin
    echo.
    echo   注意：如果手机无法访问，请检查防火墙是否放行了8080端口
) else (
    echo   未能检测到局域网IP，请使用 ipconfig 命令查看
)
echo ============================================
echo.
cd /d "%~dp0"
mvn spring-boot:run
pause
