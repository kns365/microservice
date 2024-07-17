@echo off
set "source0=C:\Projects\project\old\microservice\src\msa\main-service\config-service\target\config-service.jar"
set "source1=C:\Projects\project\old\microservice\src\msa\main-service\eureka-service\target\eureka-service.jar"
set "source2=C:\Projects\project\old\microservice\src\msa\main-service\zuul-service\target\zuul-service.jar"
set "source3=C:\Projects\project\old\microservice\src\msa\main-service\board-service\target\board-service.jar"
set "source4=C:\Projects\project\old\microservice\src\msa\main-service\auth-service\target\auth-service.jar"
set "source5=C:\Projects\project\old\microservice\src\msa\app-service\email-service\target\email-service.jar"
set "source6=C:\Projects\project\old\microservice\src\msa\app-service\gallery-service\target\gallery-service.jar"
set "source7=C:\Projects\project\old\microservice\src\msa\app-service\image-service\target\image-service.jar"
set "source8=C:\Projects\project\old\microservice\src\msa\app-service\order-service\target\order-service.jar"
set "source9=C:\Projects\project\old\microservice\src\msa\app-service\stock-service\target\stock-service.jar"
set "source10=C:\Projects\project\old\microservice\src\msa\web-service\portalapi\target\portalapi.jar"
set "dest=C:\Projects\project\old\microservice\src\msa\out"

copy "%source0%" "%dest%"
copy "%source1%" "%dest%"
copy "%source2%" "%dest%"
copy "%source3%" "%dest%"
copy "%source4%" "%dest%"
copy "%source5%" "%dest%"
copy "%source6%" "%dest%"
copy "%source7%" "%dest%"
copy "%source8%" "%dest%"
copy "%source9%" "%dest%"
copy "%source10%" "%dest%"

echo File copied successfully.
pause