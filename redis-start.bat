@echo off

set REDIS_HOME=C:\WORK\app\redis
cd %REDIS_HOME%

start redis-server.exe
timeout 5
start redis-cli.exe monitor