package com.heye.crm.server;

import com.heye.crm.server.prometheus.EnablePrometheusEndpoint;
import com.heye.crm.server.prometheus.EnableSpringBootMetricsCollector;
import io.prometheus.client.CollectorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import javax.annotation.PostConstruct;

/**
 * @author : lishuming
 */
@ImportResource("classpath:spring-context.xml")
@SpringBootApplication
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
public class HeYeServerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeYeServerApplication.class);

    static {
        //HACK Avoids duplicate metrics registration in case of Spring Boot dev-tools restarts
        CollectorRegistry.defaultRegistry.clear();
    }

    public static void main(String[] args) {
        //org.apache.ibatis.logging.LogFactory.useLog4JLogging();
        SpringApplication.run(HeYeServerApplication.class, args);
    }

    @PostConstruct
    public void start() throws Exception {
    }
}
