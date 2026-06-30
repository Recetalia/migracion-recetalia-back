package com.previsora.migracion_recetalia.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Binds to the {@code migration.safety} prefix.
 * <p>
 * Controls the {@link com.previsora.migracion_recetalia.usecase.gateway.TargetSafetyGuard}
 * that prevents the migration from ever truncating / writing into production.
 */
@ConfigurationProperties(prefix = "migration.safety")
public class SafetyProperties {

    /** Master switch. When false the guard is bypassed (NOT recommended). */
    private boolean enabled = true;

    /**
     * The target host MUST contain at least one of these tokens (case-insensitive).
     * Empty list disables the allow-list check.
     */
    private List<String> allowedTargetHosts = new ArrayList<>();

    /**
     * The target host must NOT contain any of these tokens (case-insensitive).
     * Used to hard-block known production hosts/IPs.
     */
    private List<String> blockedTargetHosts = new ArrayList<>();

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public List<String> getAllowedTargetHosts() { return allowedTargetHosts; }
    public void setAllowedTargetHosts(List<String> allowedTargetHosts) { this.allowedTargetHosts = allowedTargetHosts; }

    public List<String> getBlockedTargetHosts() { return blockedTargetHosts; }
    public void setBlockedTargetHosts(List<String> blockedTargetHosts) { this.blockedTargetHosts = blockedTargetHosts; }
}
