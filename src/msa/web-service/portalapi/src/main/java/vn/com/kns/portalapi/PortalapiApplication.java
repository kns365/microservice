package vn.com.kns.portalapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableCaching
public class PortalapiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PortalapiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
