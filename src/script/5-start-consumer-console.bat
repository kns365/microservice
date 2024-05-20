set KAFKA_HOME=C:\WORK\app\apache-kafka
cd %KAFKA_HOME%
bin\windows\kafka-console-consumer.bat --topic quickstart-events --from-beginning --bootstrap-server localhost:9092