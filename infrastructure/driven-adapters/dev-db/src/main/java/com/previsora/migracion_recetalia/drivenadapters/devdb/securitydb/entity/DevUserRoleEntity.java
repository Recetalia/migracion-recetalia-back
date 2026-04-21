package com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * R2DBC entity mapped to dev {@code securitydb.user_roles}.
 * <p>
 * Composite PK (role_id, user_id). Since Spring Data R2DBC does not support
 * composite keys natively, inserts are done via DatabaseClient raw SQL.
 */
@Table("securitydb.user_roles")
public class DevUserRoleEntity {

    @Column("role_id")
    private Long roleId;

    @Column("user_id")
    private Long userId;

    public DevUserRoleEntity() {}

    public DevUserRoleEntity(Long roleId, Long userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
