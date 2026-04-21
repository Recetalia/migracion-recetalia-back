package com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * R2DBC entity mapped to dev {@code securitydb.users}.
 * <p>
 * READ/WRITE — this entity is used for inserting derived security records.
 * The id is auto-increment bigint — set to null on insert.
 */
@Table("securitydb.users")
public class DevSecurityUserEntity {

    @Id
    private Long id;

    private String email;

    private String username;

    private String password;

    @Column("is_active")
    private boolean isActive;

    @Column("application_id")
    private Long applicationId;

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
}
