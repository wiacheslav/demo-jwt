package com.example.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo-jwt")
@Data
public class DemoJwtConfig {
    private String jwtSecretKey;
    private long ttl = 3600;
}
