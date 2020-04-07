package io.dkgj.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="app.config")
public class AppProperties {
    private Integer app;
    private String sign;
    private String accesskey;
    private String accessSecret;
    private String templateId;
}
