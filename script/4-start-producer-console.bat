if "%KAFKA_HOME%" == "" set KAFKA_HOME=C:\WORK\app\apache-kafka
cd %KAFKA_HOME%
bin\windows\kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092