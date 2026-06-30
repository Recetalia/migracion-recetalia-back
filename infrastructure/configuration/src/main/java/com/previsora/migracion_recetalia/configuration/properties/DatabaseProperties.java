package com.previsora.migracion_recetalia.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Binds to the {@code datasource} prefix in application YAML.
 * <p>
 * Contains connection parameters for both prod (source) and dev (target) databases.
 */
@ConfigurationProperties(prefix = "datasource")
public class DatabaseProperties {

    private DbConnectionProps prod = new DbConnectionProps();
    private DbConnectionProps dev = new DbConnectionProps();

    public DbConnectionProps getProd() {
        return prod;
    }

    public void setProd(DbConnectionProps prod) {
        this.prod = prod;
    }

    public DbConnectionProps getDev() {
        return dev;
    }

    public void setDev(DbConnectionProps dev) {
        this.dev = dev;
    }

    public static class DbConnectionProps {
        private String host = "localhost";
        private int port = 3306;
        private String username = "root";
        private String password = "root";
        /** r2dbc sslMode: DISABLED (local) | PREFERRED | REQUIRED (DO managed PRE) | VERIFY_CA | VERIFY_IDENTITY. */
        private String sslMode = "DISABLED";
        private SchemaNames schemas = new SchemaNames();

        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }
        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getSslMode() { return sslMode; }
        public void setSslMode(String sslMode) { this.sslMode = sslMode; }
        public SchemaNames getSchemas() { return schemas; }
        public void setSchemas(SchemaNames schemas) { this.schemas = schemas; }
    }

    public static class SchemaNames {
        private String dnma = "recetali_dnma";
        private String receta = "recetali_receta";
        private String security = "securitydb";

        public String getDnma() { return dnma; }
        public void setDnma(String dnma) { this.dnma = dnma; }
        public String getReceta() { return receta; }
        public void setReceta(String receta) { this.receta = receta; }
        public String getSecurity() { return security; }
        public void setSecurity(String security) { this.security = security; }
    }
}
