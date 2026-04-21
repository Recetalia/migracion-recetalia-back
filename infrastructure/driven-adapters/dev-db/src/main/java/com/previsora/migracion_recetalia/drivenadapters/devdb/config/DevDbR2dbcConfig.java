package com.previsora.migracion_recetalia.drivenadapters.devdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * R2DBC repository scanning for the dev-db module.
 * <p>
 * Scans only the dev repository package and uses the dev entity template.
 */
@Configuration
@EnableR2dbcRepositories(
        basePackages = {
                "com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository",
                "com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository",
                "com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.repository"
        },
        entityOperationsRef = "devR2dbcEntityTemplate"
)
public class DevDbR2dbcConfig {
}
