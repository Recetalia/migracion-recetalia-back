package com.previsora.migracion_recetalia.usecase.gateway;

/**
 * Safety guard that protects the migration TARGET (dev) database.
 * <p>
 * Before any destructive operation (TRUNCATE / clean insert) the migration must
 * be certain the target is NOT production. Implementations verify the configured
 * target host against an allow-list (expected PRE host) and a block-list
 * (forbidden production hosts), and refuse to proceed otherwise.
 * <p>
 * This is invoked both at application startup (fail-fast) and immediately before
 * truncating target tables (defence-in-depth).
 */
public interface TargetSafetyGuard {

    /**
     * Verify the configured migration target is safe to write to (i.e. it is NOT
     * production).
     *
     * @throws com.previsora.migracion_recetalia.usecase.gateway.TargetSafetyGuard.UnsafeTargetException
     *         if the target host matches a blocked (production) host, is not in the
     *         allowed target list, or equals the source database.
     */
    void verifyTargetIsNotProduction();

    /** Raised when the configured target database looks like production. */
    class UnsafeTargetException extends RuntimeException {
        public UnsafeTargetException(String message) {
            super(message);
        }
    }
}
