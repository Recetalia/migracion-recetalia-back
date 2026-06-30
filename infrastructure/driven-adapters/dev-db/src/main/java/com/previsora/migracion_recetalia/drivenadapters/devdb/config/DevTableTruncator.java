package com.previsora.migracion_recetalia.drivenadapters.devdb.config;

import com.previsora.migracion_recetalia.usecase.gateway.TableTruncator;
import com.previsora.migracion_recetalia.usecase.gateway.TargetSafetyGuard;
import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Truncates dev tables using a single connection with FK checks disabled.
 * <p>
 * Uses raw R2DBC connection to guarantee all statements (SET FK_CHECKS,
 * TRUNCATE, SET FK_CHECKS) run on the same session.
 */
@Component("devTableTruncator")
public class DevTableTruncator implements TableTruncator {

    private static final Logger log = LoggerFactory.getLogger(DevTableTruncator.class);

    private final ConnectionFactory connectionFactory;
    private final TargetSafetyGuard targetSafetyGuard;

    public DevTableTruncator(@Qualifier("devConnectionFactory") ConnectionFactory connectionFactory,
                             TargetSafetyGuard targetSafetyGuard) {
        this.connectionFactory = connectionFactory;
        this.targetSafetyGuard = targetSafetyGuard;
    }

    @Override
    public Mono<Void> truncateAll(List<String> fullyQualifiedTableNames) {
        // Defence-in-depth: never truncate unless the target is verified NOT to be production.
        // Runs inside the reactive chain so a failure propagates as onError (not at assembly time).
        return Mono.<Void>fromRunnable(targetSafetyGuard::verifyTargetIsNotProduction)
                .then(doTruncateAll(fullyQualifiedTableNames));
    }

    private Mono<Void> doTruncateAll(List<String> fullyQualifiedTableNames) {
        log.info("Truncating {} dev tables with FK checks disabled", fullyQualifiedTableNames.size());

        return Mono.usingWhen(
                Mono.from(connectionFactory.create()),
                connection -> {
                    Mono<Void> disableFK = Mono.from(connection.createStatement(
                            "SET FOREIGN_KEY_CHECKS = 0").execute())
                            .flatMapMany(result -> result.getRowsUpdated())
                            .then();

                    Mono<Void> truncates = Flux.fromIterable(fullyQualifiedTableNames)
                            .concatMap(table -> {
                                log.info("  TRUNCATE TABLE {}", table);
                                return Mono.from(connection.createStatement(
                                        "TRUNCATE TABLE " + table).execute())
                                        .flatMapMany(result -> result.getRowsUpdated())
                                        .then();
                            })
                            .then();

                    Mono<Void> enableFK = Mono.from(connection.createStatement(
                            "SET FOREIGN_KEY_CHECKS = 1").execute())
                            .flatMapMany(result -> result.getRowsUpdated())
                            .then();

                    return disableFK.then(truncates).then(enableFK);
                },
                connection -> Mono.from(connection.close())
        );
    }
}
