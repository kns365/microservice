package vn.com.kns.portalapi.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorAwareConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
//        return new AuditorAwareImpl(); //khi CreatedBy chỉ thấy lấy user SYSTEM
        return new CustomAuditorAware();
    }


}
