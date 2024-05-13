# Microservices with Spring Boot
Attention version: Do not upgrade version
Spring boot: 2.1.3.RELEASE
Spring cloud: Greenwich.SR3 (higher Hoxton not support Zuul server)

## The series of servers:

- ### 1/ Config server-8760 (Spring security, common class)
    - All other servers connect to it to receive config
    - Required username/password to connect
    - The class can imported by other module over pom file
    - JwtConfig
    - Model(ResponseDto,...)
- ### 2/ Eureka server-8761 (Discovery, Registration)
- ### 3/ Zuul server-8762 (API gateway, Spring security)
    - Zuul removed by version cloud > Hoxton
    - SecurityTokenConfig (WebSecurityConfigurerAdapter)
    - JwtTokenAuthenticationFilter (OncePerRequestFilter)
- ### 4/ Auth server-8763 (Spring security, Generation and Validation user/request by JWT)
    - JwtUsernameAndPasswordAuthenticationFilter (UsernamePasswordAuthenticationFilter)
    - SecurityCredentialsConfig (WebSecurityConfigurerAdapter)
    - UserDetailsServiceImpl (UserDetailsService)
    - JWT response in header

## The series of services:

- ### 1/ Gallery service 1-8101
- ### 2/ Gallery service 2-8102
- ### 3/ Image service 1-8201
- ### 4/ Image service 2-8202

