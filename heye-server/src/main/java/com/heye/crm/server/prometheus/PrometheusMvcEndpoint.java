package com.heye.crm.server.prometheus;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * @author : lishuming
 */
@ConfigurationProperties("endpoints.prometheus")
public class PrometheusMvcEndpoint extends EndpointMvcAdapter {

    private final PrometheusEndpoint delgate;

    public PrometheusMvcEndpoint(PrometheusEndpoint delegate) {
        super(delegate);
        this.delgate = delegate;
    }

    @RequestMapping(method = {RequestMethod.GET}, produces = {"*/*"})
    @ResponseBody
    public ResponseEntity value(
            @RequestParam(value = "name[]", required = false, defaultValue = "") Set<String> name) {
        if (!getDelegate().isEnabled()) {
            // Shouldn't happen - MVC endpoint shouldn't be registered when delegate's
            // disabled
            return getDisabledResponse();
        }

        String result = delgate.writeRegistry(name);
        return ResponseEntity.ok()
                .header(CONTENT_TYPE, TextFormat.CONTENT_TYPE_004)
                .body(result);
    }
}
