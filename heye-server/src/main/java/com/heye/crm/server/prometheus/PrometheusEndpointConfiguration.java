package com.heye.crm.server.prometheus;

import io.prometheus.client.CollectorRegistry;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lishuming
 */
@Configuration
class PrometheusEndpointConfiguration {

    @Bean
    public PrometheusEndpoint prometheusEndpoint() {
        return new PrometheusEndpoint(CollectorRegistry.defaultRegistry);
    }

    @Bean
    @ConditionalOnBean(PrometheusEndpoint.class)
    @ConditionalOnEnabledEndpoint("prometheus")
    public PrometheusMvcEndpoint prometheusEndpointFix(PrometheusEndpoint prometheusEndpoint) {
        return new PrometheusMvcEndpoint(prometheusEndpoint);
    }
}
