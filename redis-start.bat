@echo off

set REDIS_HOME=C:\WORK\app\redis
cd %REDIS_HOME%

start redis-server.exe
rem timeout 3
rem start redis-cli.exe monitor