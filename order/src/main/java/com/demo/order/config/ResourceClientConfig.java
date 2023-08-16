package com.demo.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ResourceClientConfig {

    @Value("${service.resource.url}")
    private String url;

    @Bean
    public WebClient getResourceClient() {
        return WebClient.builder().baseUrl(url).build();
    }
}
