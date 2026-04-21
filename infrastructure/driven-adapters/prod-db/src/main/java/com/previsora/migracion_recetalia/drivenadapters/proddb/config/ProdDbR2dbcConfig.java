package com.previsora.migracion_recetalia.drivenadapters.proddb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * R2DBC repository scanning for the prod-db module.
 * <p>
 * Scans only the prod repository package and uses the prod entity template.
 */
@Configuration
@EnableR2dbcRepositories(
        basePackages = {
                "com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository",
                "com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository"
        },
        entityOperationsRef = "prodR2dbcEntityTemplate"
)
public class ProdDbR2dbcConfig {
}
