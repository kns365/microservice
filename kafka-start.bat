set KAFKA_HOME=C:\WORK\app\apache-kafka
cd "script"
start 1-start-zookeeper.bat
timeout 3
start 2-start-kafka.bat
rem start create-topic.bat
rem start start-producer-console.bat
rem start start-consumer-console.bat