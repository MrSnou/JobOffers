# JobOffers 📁

REST API that aggregates job offers from external sites in one place — built with **Java**, 
a **modular monolith** structure and **hexagonal architecture**.

> ⚠️ Work in progress — portfolio project, actively developed.

## About

JobOffers is a backend application that fetches job offers from external sources 
on a schedule, stores them and exposes them through a REST API.

## Tech Stack

## Tech Stack


| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Framework | Spring Boot 4.0.6 |
| Persistence | Spring Data MongoDB |
| Documentation | Springdoc OpenAPI / Swagger UI |
| Build tool | Maven |
| Other | Lombok, Spring Actuator |

## Testing

| Type | Tools |
|------|-------|
| Unit | JUnit 5, AssertJ (no Spring context) |
| Integration | MockMvc, WireMock, Testcontainers (MongoDB) |

## Architecture

Modular monolith with hexagonal architecture (ports & adapters): a clear separation
between `domain` and `infrastructure`, package-private encapsulation, and facades
(`OfferFacade`, `UserFacade`) as the only public entry points into the domain.

```
src/main/java/com/joboffersapi/
├── domain/
│   ├── offercrud/              # offers domain — package-private
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── Offer                 # domain entity
│   │   ├── OfferFacade           # public entry point
│   │   ├── OfferFetchable        # port — external offer source
│   │   ├── OfferRepository       # port — persistence
│   │   ├── OfferService
│   │   ├── OfferMapper
│   │   └── JobOfferConfiguration
│   └── usercrud/               # users domain — package-private
│       ├── dto/
│       ├── exception/
│       ├── User
│       ├── UserFacade            # public entry point
│       ├── UserRepository        # port — persistence
│       ├── UserService
│       ├── UserMapper
│       └── UserFacadeConfiguration
├── infrastructure/
│   ├── offercrud/http/
│   │   ├── config/
│   │   ├── dto/
│   │   ├── scheduler/            # scheduled offer fetching
│   │   └── OfferFetcherRestTemplate   # adapter for OfferFetchable
│   ├── jwt/
│   │   └── JwtAuthenticator
│   ├── security/
│   └── usercrud/
└── JobOffersApplication.java
```

## Running locally

Requires JDK 17 and a running Docker daemon (integration tests use Testcontainers).

```bash
docker-compose up -d     # MongoDB
mvn clean install
mvn spring-boot:run
```

Swagger UI: `http://localhost:8081/swagger-ui.html`

## Roadmap

- [ ] Spring Security configuration + JWT authentication flow
- [ ] Redis cache for offer queries
