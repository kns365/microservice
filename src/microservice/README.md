# Microservices with Spring Boot
Attention version: Do not upgrade version
Spring boot: 2.1.3.RELEASE
Spring cloud: Greenwich.SR3 (higher Hoxton not support Zuul server)

## The series of servers:

- ### 1/ Config server-8760 (Spring security, common class)
  - Define all config for client, need user/pass to connect
  - Common dependency pom
  - Common class/model/helper(ResponseDto,...) can imported by other module over pom fil
  - JwtProvider to validate JWT (KeyStore)
- ### 2/ Eureka server-8761 (Discovery, Registration)
  - Discovery all service registry
- ### 3/ Zuul server-8762 (API gateway, Spring security)
  - Routing
  - Load balancing
  - Spring security, filter each request and Validate token
  - [Rate limit for anti ddos](https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit)
    - Type: ORIGIN, USER, URL, URL_PATTERN, ROLE, HTTP_METHOD, HTTP_HEADER
    - Repository: REDIS, JPA 
- ### 4/ Auth server-8763 (Spring security, Generation and Validation user/request by JWT)
  - Spring security, accept 2 url (getToken,refreshToken)
  - Generate accessToken, refreshToken
  - Connect DB for 3 table (User,Role,Privilege)
- ### 5/ Kafka server-9092
  - 
  - 
  - 

## The series of services:
Hystrix to fast error
Sleuth to tracking transaction log

- ### 1/ Gallery service 1-8101
- ### 2/ Gallery service 2-8102
- ### 3/ Image service 1-8201
- ### 4/ Image service 2-8202

