package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Mono;

/**
 * Output port for reading and assigning security roles in {@code dev.securitydb}.
 */
public interface SecurityUserRoleGateway {

    /**
     * Check whether the user identified by {@code email} already has {@code roleName}.
     */
    Mono<Boolean> hasRole(String email, String roleName);

    /**
     * Assign {@code roleName} to the user identified by {@code email}.
     */
    Mono<Void> assignRole(String email, String roleName);
}
