# Microservices with Spring Boot

## The series of servers:

- ### 1/ Config server-8760 (All other servers connect to it to receive config, Required username/password to connect)
- ### 2/ Eureka server-8761 (Discovery, Registration)
- ### 3/ Zuul server-8762 (API gateway, Spring security)
    - SecurityTokenConfig (WebSecurityConfigurerAdapter)
    - JwtTokenAuthenticationFilter (OncePerRequestFilter)
- ### 4/ Common server-8763 (The class can imported by other module over pom file)
    - JwtConfig
- ### 5/ Auth server-8764 (Spring security, Generation and Validation user/request by JWT)
    - JwtUsernameAndPasswordAuthenticationFilter (UsernamePasswordAuthenticationFilter)
    - SecurityCredentialsConfig (WebSecurityConfigurerAdapter)
    - UserDetailsServiceImpl (UserDetailsService)
    - JWT response in header

## The series of services:

- ### 1/ Gallery service 1-8101
- ### 2/ Gallery service 2-8102
- ### 3/ Image service 1-8201
- ### 4/ Image service 2-8202

