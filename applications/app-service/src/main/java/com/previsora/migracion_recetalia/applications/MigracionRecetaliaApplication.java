package com.previsora.migracion_recetalia.applications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.previsora.migracion_recetalia")
public class MigracionRecetaliaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MigracionRecetaliaApplication.class, args);
    }
}
