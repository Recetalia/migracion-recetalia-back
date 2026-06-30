package com.previsora.migracion_recetalia.configuration;

import com.previsora.migracion_recetalia.configuration.properties.DatabaseProperties;
import com.previsora.migracion_recetalia.configuration.properties.SafetyProperties;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import io.r2dbc.spi.Option;
import io.r2dbc.spi.Statement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Dual R2DBC configuration for prod (MariaDB) and dev (MySQL).
 * <p>
 * All beans are explicitly named with @Qualifier. There is no default schema set
 * on either connection — all SQL must use fully-qualified table names (schema.table).
 * <p>
 * Spring Boot's R2DBC autoconfiguration is disabled (see application.yml excludes).
 */
@Configuration
@EnableConfigurationProperties({DatabaseProperties.class, SafetyProperties.class})
public class R2dbcConfig {

    private final DatabaseProperties props;

    public R2dbcConfig(DatabaseProperties props) {
        this.props = props;
    }

    // ─── Prod (Source) — MariaDB 10.5 — READ-ONLY ───

    @Bean
    @Primary
    @Qualifier("prodConnectionFactory")
    public ConnectionFactory prodConnectionFactory() {
        DatabaseProperties.DbConnectionProps p = props.getProd();
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mariadb")
                .option(ConnectionFactoryOptions.HOST, p.getHost())
                .option(ConnectionFactoryOptions.PORT, p.getPort())
                .option(ConnectionFactoryOptions.USER, p.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, p.getPassword())
                .option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(30))
                .option(Option.valueOf("socketTimeout"), Duration.ofMinutes(10))
                .build();
        ConnectionFactory readOnly = readOnlyConnectionFactory(ConnectionFactories.get(options));
        return new ConnectionPool(ConnectionPoolConfiguration.builder(readOnly)
                .name("prodPool")
                .initialSize(2)
                .maxSize(20)
                .maxIdleTime(Duration.ofMinutes(30))
                .maxLifeTime(Duration.ofHours(1))
                .maxAcquireTime(Duration.ofSeconds(60))
                .build());
    }

    @Bean
    @Primary
    @Qualifier("prodDatabaseClient")
    public DatabaseClient prodDatabaseClient(
            @Qualifier("prodConnectionFactory") ConnectionFactory cf) {
        return DatabaseClient.create(cf);
    }

    @Bean
    @Primary
    @Qualifier("prodR2dbcEntityTemplate")
    public R2dbcEntityTemplate prodR2dbcEntityTemplate(
            @Qualifier("prodConnectionFactory") ConnectionFactory cf) {
        return new R2dbcEntityTemplate(cf);
    }

    @Bean
    @Qualifier("prodTransactionManager")
    public ReactiveTransactionManager prodTransactionManager(
            @Qualifier("prodConnectionFactory") ConnectionFactory cf) {
        return new R2dbcTransactionManager(cf);
    }

    // ─── Dev (Target) — MySQL 8.0 — READ/WRITE ───

    @Bean
    @Qualifier("devConnectionFactory")
    public ConnectionFactory devConnectionFactory() {
        DatabaseProperties.DbConnectionProps d = props.getDev();
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(ConnectionFactoryOptions.HOST, d.getHost())
                .option(ConnectionFactoryOptions.PORT, d.getPort())
                .option(ConnectionFactoryOptions.USER, d.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, d.getPassword())
                .option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(30))
                .option(Option.valueOf("socketTimeout"), Duration.ofMinutes(10))
                .option(Option.valueOf("sslMode"), d.getSslMode())
                .build();
        return new ConnectionPool(ConnectionPoolConfiguration.builder(ConnectionFactories.get(options))
                .name("devPool")
                .initialSize(2)
                .maxSize(20)
                .maxIdleTime(Duration.ofMinutes(30))
                .maxLifeTime(Duration.ofHours(1))
                .maxAcquireTime(Duration.ofSeconds(60))
                .build());
    }

    @Bean
    @Qualifier("devDatabaseClient")
    public DatabaseClient devDatabaseClient(
            @Qualifier("devConnectionFactory") ConnectionFactory cf) {
        return DatabaseClient.create(cf);
    }

    @Bean
    @Qualifier("devR2dbcEntityTemplate")
    public R2dbcEntityTemplate devR2dbcEntityTemplate(
            @Qualifier("devConnectionFactory") ConnectionFactory cf) {
        return new R2dbcEntityTemplate(cf);
    }

    @Bean
    @Qualifier("devTransactionManager")
    public ReactiveTransactionManager devTransactionManager(
            @Qualifier("devConnectionFactory") ConnectionFactory cf) {
        return new R2dbcTransactionManager(cf);
    }

    private ConnectionFactory readOnlyConnectionFactory(ConnectionFactory delegate) {
        return new ConnectionFactory() {
            @Override
            public Mono<io.r2dbc.spi.Connection> create() {
                return Mono.from(delegate.create())
                        .flatMap(connection -> Flux.concat(
                                        executeSessionStatement(connection, "SET SESSION TRANSACTION READ ONLY"),
                                        executeSessionStatement(connection, "SET SESSION tx_read_only = 1")
                                )
                                .then(Mono.just(connection)));
            }

            @Override
            public ConnectionFactoryMetadata getMetadata() {
                return delegate.getMetadata();
            }
        };
    }

    private Mono<Void> executeSessionStatement(io.r2dbc.spi.Connection connection, String sql) {
        Statement statement = connection.createStatement(sql);
        return Flux.from(statement.execute())
                .flatMap(result -> result.getRowsUpdated())
                .then();
    }
}
