package com.testus.testus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${cors.allow.origin-pattern}")
    private String[] allowedOrigins;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*")
                .allowedHeaders("Authorization", "Refreshtoken", "Cache-Control", "Content-Type", "Accept", "*")
                .exposedHeaders("Authorization", "Refreshtoken", "Cache-Control", "Content-Type", "Accept", "*")
                .allowCredentials(false)
                .maxAge(6000);
    }
}