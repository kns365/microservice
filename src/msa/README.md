# Microservices with Spring Boot

Attention version: Do not upgrade version Spring boot: 2.3.12.RELEASE Spring cloud: Hoxton.SR12 (higher Hoxton not support Zuul 1)
Spring admin: 2.3.1

## The series of servers:

- ### 1/ Config service-8760 (config-server,eureka-client,admin-client,sleuth,hystrix,security)
    - Define all config for client, need user/pass to connect
    - Secure frontend
- ### 2/ [Eureka server-8761 (Discovery, Registration)](http://localhost:8761/) (eureka-server,config-client,admin-client,sleuth,hystrix,security)
    - Discovery all service registry
    - Secure frontend
- ### 3/ Zuul1 server-8762 (zuul-server,rate-limit,data-redis,springdoc,config-client,eureka-client,admin-client,sleuth,hystrix,security)
    - API gateway
    - Routing
    - Load balancing by ribbon
    - Rate limit
    - Validate JWT
    - [Rate limit for anti ddos](https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit)
        - Type: ORIGIN, USER, URL, URL_PATTERN, ROLE, HTTP_METHOD, HTTP_HEADER
        - Repository: REDIS, JPA
    - Swagger centralize
- ### 4/ Board service-8763 (admin-server,hystrix-dashboard,turbine-stream,stream-kafka,config-client,eureka-client,sleuth,hystrix,security)
    - [Spring Admin server](http://localhost:8763/admin)
    - [Hystrix dashboard](http://localhost:8763/hystrix/monitor?stream=http://localhost:8763)
    - [Turbine stream](http://localhost:8763) Kafka stream, combine all hystrix stream into one
    - Secure frontend
- ### 5/ Auth service-8764 (config-client,eureka-client,admin-client,sleuth,hystrix,security)
    - Accept 2 url: getToken, refreshToken
    - Generate JWT
    - Connect DB: User, Role, Privilege
- ### 6/ Common pack- (config-client,security)
    - application-[dev/uat/prod].properties
    - Chứa tất cả config kết nối đến hệ thống khác, phân tách các môi trường dev, uat, prod.
    - Common class/model/helper(ResponseDto,...) can imported by jar in pom
    - JwtProvider to validate JWT (KeyStore)
- ### 7/ Log service-8765 (kafka-comsumer,config-client,eureka-client,admin-client,sleuth,hystrix,security)
    - Kafka consumer
    - Receive log event from other services and write to DB

## Required server:

Kakfa: localhost:9092 Redis: localhost:6379

## Error:

Check swagger gallery, show ra nhiều url không đúng

## The series of services:

Sleuth to tracking transaction log Spring AOP: add HystrixCommand for all rest controller (not working)

- ### 1/ Gallery service 1-8101
- ### 2/ Gallery service 2-8102
- ### 3/ Image service 1-8201
- ### 4/ Image service 2-8202

