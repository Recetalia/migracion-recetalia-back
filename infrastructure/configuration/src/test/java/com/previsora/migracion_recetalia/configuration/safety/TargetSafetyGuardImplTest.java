package com.previsora.migracion_recetalia.configuration.safety;

import com.previsora.migracion_recetalia.configuration.properties.DatabaseProperties;
import com.previsora.migracion_recetalia.configuration.properties.SafetyProperties;
import com.previsora.migracion_recetalia.usecase.gateway.TargetSafetyGuard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TargetSafetyGuardImplTest {

    private static final String PRE_HOST =
            "recetaliadbpreproduccion-do-user-2735981-0.i.db.ondigitalocean.com";
    private static final String PROD_IP = "143.110.212.167";

    private SafetyProperties safety() {
        SafetyProperties s = new SafetyProperties();
        s.setEnabled(true);
        s.setAllowedTargetHosts(List.of("recetaliadbpreproduccion", "localhost", "127.0.0.1"));
        s.setBlockedTargetHosts(List.of(PROD_IP, "api.recetadigital.uy"));
        return s;
    }

    private DatabaseProperties dbProps(String sourceHost, int sourcePort, String targetHost, int targetPort) {
        DatabaseProperties p = new DatabaseProperties();
        p.getProd().setHost(sourceHost);
        p.getProd().setPort(sourcePort);
        p.getDev().setHost(targetHost);
        p.getDev().setPort(targetPort);
        return p;
    }

    private TargetSafetyGuardImpl guard(DatabaseProperties db, SafetyProperties safety) {
        return new TargetSafetyGuardImpl(db, safety);
    }

    @Test
    void allowsPreTargetWithProdSource() {
        var g = guard(dbProps("localhost", 3308, PRE_HOST, 25060), safety());
        assertDoesNotThrow(g::verifyTargetIsNotProduction);
    }

    @Test
    void allowsLocalTargetWithLocalSourceOnDifferentPort() {
        var g = guard(dbProps("localhost", 3308, "localhost", 3309), safety());
        assertDoesNotThrow(g::verifyTargetIsNotProduction);
    }

    @Test
    void blocksProductionIpTarget() {
        var g = guard(dbProps("localhost", 3308, PROD_IP, 3306), safety());
        assertThrows(TargetSafetyGuard.UnsafeTargetException.class, g::verifyTargetIsNotProduction);
    }

    @Test
    void blocksProductionDomainTarget() {
        var g = guard(dbProps("localhost", 3308, "db.api.recetadigital.uy", 3306), safety());
        assertThrows(TargetSafetyGuard.UnsafeTargetException.class, g::verifyTargetIsNotProduction);
    }

    @Test
    void blocksTargetEqualToSource() {
        var g = guard(dbProps("localhost", 3308, "localhost", 3308), safety());
        assertThrows(TargetSafetyGuard.UnsafeTargetException.class, g::verifyTargetIsNotProduction);
    }

    @Test
    void blocksTargetNotInAllowList() {
        var g = guard(dbProps("localhost", 3308, "some-random-db.example.com", 3306), safety());
        assertThrows(TargetSafetyGuard.UnsafeTargetException.class, g::verifyTargetIsNotProduction);
    }

    @Test
    void bypassesWhenDisabled() {
        SafetyProperties disabled = safety();
        disabled.setEnabled(false);
        // even a production target is allowed when the guard is off
        var g = guard(dbProps("localhost", 3308, PROD_IP, 3306), disabled);
        assertDoesNotThrow(g::verifyTargetIsNotProduction);
    }
}
