set KAFKA_HOME=%APP_HOME%\apache-kafka
cd %KAFKA_HOME%
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
timeout 3
pause