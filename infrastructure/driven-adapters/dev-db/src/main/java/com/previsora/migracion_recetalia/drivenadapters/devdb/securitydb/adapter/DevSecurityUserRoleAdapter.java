package com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.adapter;

import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.SecurityUserRoleGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Dev security role adapter backed by {@code securitydb.roles} and {@code securitydb.user_roles}.
 */
@Component
public class DevSecurityUserRoleAdapter implements SecurityUserRoleGateway {

    private final DatabaseClient databaseClient;

    public DevSecurityUserRoleAdapter(@Qualifier("devDatabaseClient") DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Boolean> hasRole(String email, String roleName) {
        return databaseClient.sql("""
                        SELECT COUNT(*) AS total
                        FROM securitydb.user_roles ur
                        JOIN securitydb.users u ON u.id = ur.user_id
                        JOIN securitydb.roles r ON r.id = ur.role_id
                        WHERE u.email = :email AND r.name = :roleName
                        """)
                .bind("email", email)
                .bind("roleName", roleName)
                .map((row, metadata) -> row.get("total", Long.class))
                .one()
                .map(total -> total != null && total > 0);
    }

    @Override
    public Mono<Void> assignRole(String email, String roleName) {
        return resolveUserId(email)
                .zipWith(resolveRoleId(roleName))
                .flatMap(ids -> databaseClient.sql("""
                                INSERT INTO securitydb.user_roles (role_id, user_id)
                                VALUES (:roleId, :userId)
                                """)
                        .bind("roleId", ids.getT2())
                        .bind("userId", ids.getT1())
                        .fetch()
                        .rowsUpdated())
                .filter(updated -> updated > 0)
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "Failed to assign role " + roleName + " to security user " + email)))
                .then();
    }

    private Mono<Long> resolveUserId(String email) {
        return databaseClient.sql("SELECT id FROM securitydb.users WHERE email = :email")
                .bind("email", email)
                .map((row, metadata) -> row.get("id", Long.class))
                .one()
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "No security user found for email " + email)));
    }

    private Mono<Long> resolveRoleId(String roleName) {
        return databaseClient.sql("SELECT id FROM securitydb.roles WHERE name = :roleName")
                .bind("roleName", roleName)
                .map((row, metadata) -> row.get("id", Long.class))
                .one()
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "No security role found with name " + roleName)));
    }
}
