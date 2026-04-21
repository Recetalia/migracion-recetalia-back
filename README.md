revolutionary.parrotfish.wkwz@protectsmail.net
Digital2025**

 Database: recetali_receta (Main Application)

  Level 0 — No dependencies
  1. country
  2. localities
  3. regions
  4. file
  5. medic_especiality
  6. medical_provider_type
  7. franchise (depends on file for logo — move to Level 1 if that FK is enforced)
  8. notification_type
  9. droug
  10. vademecum
  11. version
  12. trigger
  13. notification_old
  14. medical_provider ok

  Level 1 — Depends on Level 0

  15. user → file ok
  16. patient → country, file, localities 0k
  17. medic → medic_especiality, country, localities ok
  18. pharmacy → localities, franchise, country, file
  19. laboratory → file
  20. notification → notification_type
  21. notification_template → notification_type
  22. log → user

  Level 2 — Depends on Level 1

  23. pharmacy_dispenser → pharmacy
  24. prescription → patient, medic
  25. product → laboratory

  Level 3 — Depends on Level 2

  26. dispensation → pharmacy_dispenser, country, pharmacy, prescription

  ---
  Database: recetali_dnma (National Medication Dictionary)

  Level 0 — No dependencies

  1. unidad
  2. sustancia
  3. ffa
  4. tf
  5. laboratorio
  6. via_admin
  7. dnma_file_migration_history

  Level 1 — Depends on Level 0

  8. vmp → unidad
  9. ffe → ffa

  Level 2 — Depends on Level 1

  10. amp → vmp, tf, unidad, laboratorio
  11. vmpp → vmp
  12. vmp_sustancia → vmp, sustancia, unidad
  13. vmp_ffa → ffa, vmp
  14. vmp_vias_admin → via_admin, vmp

  Level 3 — Depends on Level 2

  15. ampp → vmpp, amp
  16. amp_ffe → amp, ffe

  Level 4 — Depends on Level 3

  17. ampp_gting → ampp

  ---
  Database: securitydb (Authentication)

  Level 0 — No dependencies

  1. applications
  2. roles
  3. flyway_schema_history

  Level 1 — Depends on Level 0

  4. users → applications

  Level 2 — Depends on Level 1

  5. user_roles → users, roles
  6. password_reset_tokens → users






# Migracion Recetalia Back

Microservice for data migration from the **Recetalia** system. It reads data from a production MariaDB database (source), transforms it, and writes it to a development MySQL database (target). It exposes REST endpoints to start and monitor the migration process.

**Migration direction: prod (MariaDB 10.5) -> dev (MySQL 8.0)**

---

## Architecture

This project follows **Hexagonal Architecture (Ports and Adapters)** with a clear separation between business logic and infrastructure.

```
┌─────────────────────────────────────────────────────────────────┐
│                        app-service (Boot)                       │
│                    Spring Boot entry point                      │
├─────────────────────────────────────────────────────────────────┤
│                        configuration                            │
│                  Bean wiring and DI setup                       │
├──────────────┬──────────────────────────────────┬───────────────┤
│ reactive-web │           usecase                │   utility     │
│  (REST API)  │    (Business logic)              │  (Helpers)    │
│              │                                  │               │
│              ├──────────────────────────────────┤               │
│              │            model                 │               │
│              │     (Pure entities & DTOs)       │               │
├──────────────┴──────────────────────────────────┴───────────────┤
│                    Driven Adapters (Infrastructure)             │
│  ┌───────────────┐  ┌───────────────┐  ┌────────────────────┐  │
│  │    prod-db    │  │    dev-db     │  │   rest-consumer    │  │
│  │  MariaDB 10.5 │  │  MySQL 8.0   │  │ External services  │  │
│  │   (Source)    │  │   (Target)   │  │                    │  │
│  └───────────────┘  └───────────────┘  └────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

---

## Multi-Database Connection

This project connects to **two database servers** at the same time:

### Source: prod-db (MariaDB 10.5 - port 3308)
| Schema           | Access     | Description                    |
|------------------|------------|--------------------------------|
| `recetali_dnma`  | Read-only  | DNMA domain data               |
| `recetali_receta`| Read-only  | Recipe/prescription data       |

### Target: dev-db (MySQL 8.0 - port 3309)
| Schema            | Access      | Description                    |
|-------------------|-------------|--------------------------------|
| `recetali_dnma`   | Read/Write  | Migrated DNMA data             |
| `recetali_receta` | Read/Write  | Migrated recipe data           |
| `securitydb`      | Read/Write  | Security and authentication    |

Each database adapter has its own independent R2DBC configuration (`ConnectionFactory`), set up in the `configuration` module. Credentials are managed through environment variables per profile (`application-{profile}.yml`).

---

## Modules

### `applications/app-service`
**Layer:** Application (Boot)

Spring Boot entry point. It puts together all modules, loads the profile configuration (`local`, `dev`, `beta`, `prod`) and creates the executable JAR. It does not contain any business logic.

---

### `domain/model`
**Layer:** Domain Model

Domain entities, DTOs, enums and error models. It is **pure Java** with no Spring dependencies. All other modules depend on this one. It defines the data structures that represent both the source data (prod) and the transformed target data (dev).

---

### `domain/usecase`
**Layer:** Domain / Use Case

Contains the **migration business rules**:
- Use case interfaces (input ports)
- Migration orchestration logic implementations
- Output port interfaces (gateways) for data access
- Data transformation and validation logic between schemas

It only depends on `model` and abstract interfaces (ports). It does not know about infrastructure.

---

### `infrastructure/entry-points/reactive-web`
**Layer:** Entry Point (Driving Adapter)

Exposes **REST endpoints** using Spring WebFlux:
- Controllers to start and monitor migrations
- Global exception handling
- Maps HTTP requests to use cases

---

### `infrastructure/driven-adapters/prod-db`
**Layer:** Driven Adapter (Source Database)

Persistence adapter for the **production MariaDB 10.5** source database:
- Read-only Spring Data R2DBC repositories
- Entities that map to `recetali_dnma` and `recetali_receta` tables
- Entity to DTO mappers
- Dedicated R2DBC configuration for the MariaDB connection
- Implements the read output ports defined in `usecase`

---

### `infrastructure/driven-adapters/dev-db`
**Layer:** Driven Adapter (Target Database)

Persistence adapter for the **development MySQL 8.0** target database:
- Read/write Spring Data R2DBC repositories
- Entities that map to `recetali_dnma`, `recetali_receta` and `securitydb` tables
- Entity to DTO mappers
- Dedicated R2DBC configuration for the MySQL connection
- Implements the write output ports defined in `usecase`

---

### `infrastructure/driven-adapters/rest-consumer`
**Layer:** Driven Adapter (HTTP)

Adapter for **external REST integrations** via WebClient:
- Reactive HTTP clients for external services
- Connection and timeout configuration
- Implements output ports that consume external APIs

---

### `infrastructure/helpers/utility`
**Layer:** Helper

**Shared utilities** across modules:
- Reusable constants
- Cross-cutting helpers
- Generic technical components

---

### `infrastructure/configuration`
**Layer:** Configuration

**Central bean wiring and cross-cutting configuration**:
- Dependency injection setup
- Multi-database R2DBC configuration (MariaDB source + MySQL target)
- OpenAPI/Swagger configuration
- WebClient configuration

---

## Tech Stack

| Component     | Technology                          |
|---------------|-------------------------------------|
| Language      | Java 21                             |
| Framework     | Spring Boot 3.3.5 (WebFlux)        |
| Build         | Gradle 9.2.1 (wrapper)             |
| Source DB     | MariaDB 10.5 via R2DBC (reactive)  |
| Target DB     | MySQL 8.0 via R2DBC (reactive)     |
| Web           | Spring WebFlux (non-blocking)      |
| API Docs      | SpringDoc OpenAPI 2.5.0            |
| Mapping       | MapStruct 1.5.5 + ModelMapper 3.2.0 |
| Code gen      | Lombok 1.18.36                     |
| Testing       | JUnit 5 + Reactor Test             |
| Quality       | JaCoCo + SonarQube                 |
| Container     | Docker (multi-stage)               |
| CI/CD         | Azure Pipelines                    |

---

## Local Database Setup

The source and target databases run via Docker Compose from the `dump_recetalia` directory:

```bash
# Start both databases
docker-compose up -d

# Source (prod): MariaDB 10.5 -> localhost:3308
# Target (dev):  MySQL 8.0    -> localhost:3309
# Both use root/root credentials
```

---

## Execution Profiles

| Profile | Description                                |
|---------|--------------------------------------------|
| `local` | Local development with hardcoded credentials |
| `dev`   | Development environment (env variables)    |
| `beta`  | Staging / pre-production environment       |
| `prod`  | Production (Swagger disabled)              |

---

## Commands

```bash
# Build all modules
./gradlew clean build

# Run locally
./gradlew :app-service:bootRun --args='--spring.profiles.active=local'

# Run tests
./gradlew test

# Coverage report
./gradlew jacocoTestReport

# SonarQube analysis
./gradlew sonarqube

# Docker build
docker build --build-arg PROFILE="dev" -t migracion-recetalia-back:latest .
```

---

## Directory Structure

```
migracion-recetalia-back/
├── applications/
│   └── app-service/                    # Spring Boot entry point
├── domain/
│   ├── model/                          # Entities, DTOs, enums, errors
│   └── usecase/                        # Business logic and ports
├── infrastructure/
│   ├── entry-points/
│   │   └── reactive-web/              # REST controllers (WebFlux)
│   ├── driven-adapters/
│   │   ├── prod-db/                   # Source: MariaDB 10.5 (read-only)
│   │   ├── dev-db/                    # Target: MySQL 8.0 (read/write)
│   │   └── rest-consumer/             # External HTTP clients
│   ├── helpers/
│   │   └── utility/                   # Shared utilities
│   └── configuration/                 # Central configuration and beans
├── gradle/wrapper/                    # Gradle wrapper
├── build.gradle                       # Root build file
├── settings.gradle                    # Module definitions
└── README.md
```
