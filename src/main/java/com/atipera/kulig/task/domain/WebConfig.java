package com.atipera.kulig.task.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class WebConfig {
    @Value("${url.github}")
    private String baseUrl;

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
