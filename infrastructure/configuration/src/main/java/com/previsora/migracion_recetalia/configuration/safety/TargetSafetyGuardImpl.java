package com.previsora.migracion_recetalia.configuration.safety;

import com.previsora.migracion_recetalia.configuration.properties.DatabaseProperties;
import com.previsora.migracion_recetalia.configuration.properties.SafetyProperties;
import com.previsora.migracion_recetalia.usecase.gateway.TargetSafetyGuard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Default {@link TargetSafetyGuard}.
 * <p>
 * Verifies the configured migration target (dev) host BEFORE any destructive
 * operation, and again at application startup (fail-fast). Rules, in order:
 * <ol>
 *   <li>If the guard is disabled, log a loud warning and allow.</li>
 *   <li>If the target host contains any blocked token (a production host), abort.</li>
 *   <li>If the target host:port equals the source (prod) host:port, abort.</li>
 *   <li>If an allow-list is configured and the target matches none of it, abort.</li>
 * </ol>
 */
@Component("targetSafetyGuard")
public class TargetSafetyGuardImpl implements TargetSafetyGuard, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(TargetSafetyGuardImpl.class);

    private final DatabaseProperties databaseProperties;
    private final SafetyProperties safetyProperties;

    public TargetSafetyGuardImpl(DatabaseProperties databaseProperties, SafetyProperties safetyProperties) {
        this.databaseProperties = databaseProperties;
        this.safetyProperties = safetyProperties;
    }

    /** Fail-fast: the application context will not start against an unsafe target. */
    @Override
    public void afterPropertiesSet() {
        verifyTargetIsNotProduction();
    }

    @Override
    public void verifyTargetIsNotProduction() {
        if (!safetyProperties.isEnabled()) {
            log.warn("⚠️  migration.safety.enabled=false — TARGET SAFETY GUARD IS DISABLED. "
                    + "Truncate/insert can run against ANY host, including production.");
            return;
        }

        DatabaseProperties.DbConnectionProps target = databaseProperties.getDev();
        DatabaseProperties.DbConnectionProps source = databaseProperties.getProd();

        String targetHost = normalize(target.getHost());
        int targetPort = target.getPort();
        String sourceHost = normalize(source.getHost());

        // 1) Hard block: target must not be a known production host.
        for (String blocked : safetyProperties.getBlockedTargetHosts()) {
            String token = normalize(blocked);
            if (!token.isEmpty() && targetHost.contains(token)) {
                throw new UnsafeTargetException(
                        "ABORT: migration target host '" + targetHost + "' matches BLOCKED production host token '"
                                + token + "'. Refusing to write to production.");
            }
        }

        // 2) The target must never be the same database as the source (prod).
        if (targetHost.equals(sourceHost) && targetPort == source.getPort()) {
            throw new UnsafeTargetException(
                    "ABORT: migration target (" + targetHost + ":" + targetPort
                            + ") is the SAME as the source/prod database. Refusing to write onto the source.");
        }

        // 3) Allow-list: if configured, the target must match one of the approved hosts.
        if (!safetyProperties.getAllowedTargetHosts().isEmpty()) {
            boolean allowed = safetyProperties.getAllowedTargetHosts().stream()
                    .map(this::normalize)
                    .anyMatch(token -> !token.isEmpty() && targetHost.contains(token));
            if (!allowed) {
                throw new UnsafeTargetException(
                        "ABORT: migration target host '" + targetHost + "' is not in the allowed target list "
                                + safetyProperties.getAllowedTargetHosts() + ". Refusing to proceed.");
            }
        }

        log.info("✅ Target safety check PASSED — migration target is '{}:{}' (not production).",
                targetHost, targetPort);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
