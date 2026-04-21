package com.previsora.migracion_recetalia.utility;

/**
 * Shared constants for migration operations.
 */
public final class MigrationConstants {

    private MigrationConstants() {
        // utility class
    }

    // ── Migration names ──
    public static final String MIGRATION_RECETA_USER = "receta-user";
    public static final String MIGRATION_SECURITY_USERS = "security-users";

    // ── Schema names ──
    public static final String SCHEMA_RECETA = "recetali_receta";
    public static final String SCHEMA_DNMA = "recetali_dnma";
    public static final String SCHEMA_SECURITY = "securitydb";

    // ── Table names (fully qualified) ──
    public static final String TABLE_RECETA_USER = SCHEMA_RECETA + ".`user`";
    public static final String TABLE_SECURITY_USERS = SCHEMA_SECURITY + ".users";

    // ── DNMA table names ──
    public static final String TABLE_DNMA_FFA = SCHEMA_DNMA + ".ffa";
    public static final String TABLE_DNMA_LABORATORIO = SCHEMA_DNMA + ".laboratorio";
    public static final String TABLE_DNMA_SUSTANCIA = SCHEMA_DNMA + ".sustancia";
    public static final String TABLE_DNMA_TF = SCHEMA_DNMA + ".tf";
    public static final String TABLE_DNMA_UNIDAD = SCHEMA_DNMA + ".unidad";
    public static final String TABLE_DNMA_VIA_ADMIN = SCHEMA_DNMA + ".via_admin";
    public static final String TABLE_DNMA_FFE = SCHEMA_DNMA + ".ffe";
    public static final String TABLE_DNMA_VMP = SCHEMA_DNMA + ".vmp";
    public static final String TABLE_DNMA_AMP = SCHEMA_DNMA + ".amp";
    public static final String TABLE_DNMA_AMP_FFE = SCHEMA_DNMA + ".amp_ffe";
    public static final String TABLE_DNMA_VMP_FFA = SCHEMA_DNMA + ".vmp_ffa";
    public static final String TABLE_DNMA_VMP_SUSTANCIA = SCHEMA_DNMA + ".vmp_sustancia";
    public static final String TABLE_DNMA_VMP_VIAS_ADMIN = SCHEMA_DNMA + ".vmp_vias_admin";
    public static final String TABLE_DNMA_VMPP = SCHEMA_DNMA + ".vmpp";
    public static final String TABLE_DNMA_AMPP = SCHEMA_DNMA + ".ampp";
    public static final String TABLE_DNMA_AMPP_GTING = SCHEMA_DNMA + ".ampp_gting";
    public static final String TABLE_DNMA_FILE_MIGRATION_HISTORY = SCHEMA_DNMA + ".dnma_file_migration_history";

    // ── Receta Level 0 table names (no FK dependencies) ──
    public static final String TABLE_RECETA_COUNTRY = SCHEMA_RECETA + ".country";
    public static final String TABLE_RECETA_REGIONS = SCHEMA_RECETA + ".regions";
    public static final String TABLE_RECETA_LOCALITIES = SCHEMA_RECETA + ".localities";
    public static final String TABLE_RECETA_FILE = SCHEMA_RECETA + ".file";
    public static final String TABLE_RECETA_MEDIC_ESPECIALITY = SCHEMA_RECETA + ".medic_especiality";
    public static final String TABLE_RECETA_MEDICAL_PROVIDER_TYPE = SCHEMA_RECETA + ".medical_provider_type";
    public static final String TABLE_RECETA_FRANCHISE = SCHEMA_RECETA + ".franchise";
    public static final String TABLE_RECETA_NOTIFICATION_TYPE = SCHEMA_RECETA + ".notification_type";
    public static final String TABLE_RECETA_DROUG = SCHEMA_RECETA + ".droug";
    public static final String TABLE_RECETA_VADEMECUM = SCHEMA_RECETA + ".vademecum";
    public static final String TABLE_RECETA_VERSION = SCHEMA_RECETA + ".version";
    public static final String TABLE_RECETA_TRIGGER = SCHEMA_RECETA + ".`trigger`";
    public static final String TABLE_RECETA_NOTIFICATION_OLD = SCHEMA_RECETA + ".notification_old";
    public static final String TABLE_RECETA_MEDICAL_PROVIDER = SCHEMA_RECETA + ".medical_provider";

    // ── Defaults ──
    public static final int DEFAULT_BATCH_SIZE = 500;

    // ── Security defaults ──
    /** Default application_id for migrated users in securitydb.users */
    public static final long DEFAULT_SECURITY_APPLICATION_ID = 2L;
}
