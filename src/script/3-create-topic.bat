set KAFKA_HOME=C:\WORK\app\apache-kafka
cd %KAFKA_HOME%
bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092