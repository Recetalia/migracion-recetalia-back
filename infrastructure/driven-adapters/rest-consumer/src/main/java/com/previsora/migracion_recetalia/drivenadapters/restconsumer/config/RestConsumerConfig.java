package com.previsora.migracion_recetalia.drivenadapters.restconsumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient configuration for external REST integrations.
 * <p>
 * Currently a placeholder — no external services are integrated yet.
 * Add WebClient beans here as external API integrations are needed.
 */
@Configuration
public class RestConsumerConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();
    }
}
