package com.previsora.migracion_recetalia.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * SpringDoc OpenAPI 2.5 configuration.
 */
@Configuration
public class OpenApiConfig {

    @Value("${openapi.server-url:http://localhost:8094/api}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Migracion Recetalia API")
                        .version("1.0.0")
                        .description("REST API for migrating data from Recetalia prod (MariaDB 10.5) to dev (MySQL 8.0)"))
                .servers(List.of(new Server().url(serverUrl)));
    }
}
