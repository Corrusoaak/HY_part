package com.heye.crm.server.prometheus;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author : lishuming
 */
@Component
public class PrometheusEndpointFix extends EndpointMvcAdapter {

    private final PrometheusEndpoint delegate;

    @Autowired
    public PrometheusEndpointFix(PrometheusEndpoint delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> invoke() {
        if (delegate.isEnabled()) {
            String result = delegate.invoke();
            return ResponseEntity.ok()
                    .header(CONTENT_TYPE, TextFormat.CONTENT_TYPE_004)
                    .body(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
