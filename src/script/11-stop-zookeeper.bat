if "%KAFKA_HOME%" == "" set KAFKA_HOME=C:\WORK\app\apache-kafka
cd %KAFKA_HOME%
bin\windows\zookeeper-server-stop.bat