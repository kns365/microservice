# Microservices with Spring Boot
Attention version: Do not upgrade version
Spring boot: 2.3.12.RELEASE
Spring cloud: Hoxton.SR12 (higher Hoxton not support Zuul 1)
Spring admin: 2.3.1

## The series of servers:

- ### 1/ Config service-8760 (Spring: web, eureka-client, sleuth, config-client, aop & retry, actuator, admin-client, hystrix ; optional: Security, config-server)
  - Define all config for client, need user/pass to connect
  - Common dependency pom
  - Common class/model/helper(ResponseDto,...) can imported by other module over pom fil
  - JwtProvider to validate JWT (KeyStore)
  - Secure frontend
- ### 2/ [Eureka server-8761 (Discovery, Registration)](http://localhost:8761/) (Config service ignore eureka-client, eureka-server, Security)
  - Discovery all service registry
  - Secure frontend
- ### 3/ Zuul1 server-8762 (Config service, Security, Zuul, zuul-ratelimit, data-redis, springdoc)
  - API gateway
  - Routing
  - Load balancing by ribbon
  - Rate limit
  - Validate JWT
  - [Rate limit for anti ddos](https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit)
    - Type: ORIGIN, USER, URL, URL_PATTERN, ROLE, HTTP_METHOD, HTTP_HEADER
    - Repository: REDIS, JPA
  - Swagger centralize
- ### 4/ Board service-8763 (Config service ignore admin-client, Security, admin-server, hystrix-dashboard, turbine-stream, stream-kafka)
  - [Spring Admin server](http://localhost:8763/admin)
  - [Hystrix dashboard](http://localhost:8763/hystrix/monitor?stream=http://localhost:8763)
  - [Turbine stream](http://localhost:8763) Kafka stream, combine all hystrix stream into one
  - Secure frontend
- ### 5/ Auth service-8764 (Config service, Security)
  - Accept 2 url: getToken, refreshToken
  - Generate JWT
  - Connect DB: User, Role, Privilege
- ### 6/ Common pack-8764 (Config service, Security)
  - application-[dev/uat/prod].properties
  - Chứa tất cả config kết nối đến hệ thống khác, phân tách các môi trường dev, uat, prod.
    
## Required server:
Kakfa: localhost:9092
Redis: localhost:6379

## Error:
Check swagger gallery, show ra nhiều url không đúng

## The series of services:
Sleuth to tracking transaction log
Spring AOP: add HystrixCommand for all rest controller (not working)

- ### 1/ Gallery service 1-8101
- ### 2/ Gallery service 2-8102
- ### 3/ Image service 1-8201
- ### 4/ Image service 2-8202

