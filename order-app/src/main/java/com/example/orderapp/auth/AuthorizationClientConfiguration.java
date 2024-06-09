package com.example.orderapp.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "app.auth.client")
@Getter
@Setter
public class AuthorizationClientConfiguration {
    private String baseUrl;
    private Duration timeout;
}
