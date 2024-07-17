@echo off
cd C:\Projects\project\old\microservice\src\msa\out

echo Welcome to start MSA

echo start config-service
start cmd /k "java -jar config-service.jar"
timeout 10

echo start eureka-service
start cmd /k "java -jar eureka-service.jar"
timeout 5

echo start zuul-service
start cmd /k "java -jar zuul-service.jar"
timeout 5

echo start board-service
start cmd /k "java -jar board-service.jar"
timeout 5

echo start auth-service
start cmd /k "java -jar auth-service.jar"
timeout 5

echo start email-service
start cmd /k "java -jar email-service.jar"
timeout 5

echo start gallery-service
start cmd /k "java -jar gallery-service.jar"
timeout 5

echo start image-service
start cmd /k "java -jar image-service.jar"
timeout 5

echo start order-service
start cmd /k "java -jar order-service.jar"
timeout 5

echo start stock-service
start cmd /k "java -jar stock-service.jar"
timeout 5


pause