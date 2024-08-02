package vn.com.kns.portalapi.web.config;

import com.kns.apps.msa.commonpack.application.helper.LogHelper;
import com.kns.apps.msa.commonpack.application.service.auditLog.LogProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Author: KhoaNguyen
 * DateTime: 8/1/2024 11:06 AM
 */
@Configuration
public class CommonPackConfig {
    @Bean
    public LogProducer logProducer() {
        return new LogProducer();
    }

    @Autowired
    private Environment env;
    @Autowired
    private LogProducer logProducer;
    @Bean
    public LogHelper logHelper() {
        return new LogHelper(logProducer, env);
    }

}
