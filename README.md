# PokEspeare

A Spring Boot REST API that returns Pokémon descriptions rewritten in Shakespearean style.

---

## Tech Stack

- Java 17
- Spring Boot 3.4.1
- Maven
- Docker

---

## Run with Docker

### Prerequisites
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running

### Commands

```bash
# Clone the repository
git clone https://github.com/Anita-Liberatore/PokEspeare.git
cd PokEspeare

# Build and start
docker compose up --build
```

The API will be available at `http://localhost:8080`.

To stop the container:

```bash
docker compose down
```

---

## Endpoints

### GET `/pokemon/{name}`

Returns standard Pokémon information fetched from the PokéAPI.

**Request**
```
GET /pokemon/mewtwo
```

**Response `200 OK`**
```json
{
  "name": "mewtwo",
  "description": "It was created by a scientist after years of horrific gene splicing and DNA engineering experiments.",
  "habitat": "rare",
  "isLegendary": true
}
```

**Response `404 Not Found`** when the Pokémon name does not exist.

---

### GET `/pokemon/translated/{name}`

Returns Pokémon information with the description translated using the following rules:
- **Yoda** style if the Pokémon is legendary or its habitat is `cave`
- **Shakespeare** style for all other Pokémon
- Falls back to the standard description if the translation service is unavailable

**Request**
```
GET /pokemon/translated/mewtwo
```

**Response `200 OK`**
```json
{
  "name": "mewtwo",
  "description": "By a one of powerful knowledge after years of terrible gene fusing and DNA crafting trials, made it was, young one.",
  "habitat": "rare",
  "isLegendary": true
}
```

**Response `404 Not Found`** when the Pokémon name does not exist.

---

## API Documentation

Interactive API docs are available via Swagger UI once the application is running:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

---

## Run locally (without Docker)

### Prerequisites
- Java 17+
- Maven 3.9+

```bash
mvn spring-boot:run
```

---

## Tests

```bash
mvn test
```

---

## Production Considerations

This implementation is intentionally designed as a demo for a low DAU scenario (3-4 concurrent users). The following points describe what would change in a production-ready setup:

- **API versioning**: In production, APIs should be versioned following REST best practices (e.g. `/v1/pokemon/...`) to allow non-breaking evolution and maintain backward compatibility with existing clients.

- **Scalability & infrastructure**: For a higher DAU, the architecture would shift towards an API gateway for routing and rate limiting, a load balancer in front of multiple replicas of the microservice (e.g. via Kubernetes pod scaling), and a distributed Redis cache instead of the current in-memory Caffeine cache so cached translations survive restarts and are shared across all instances.

- **FunTranslations resilience**: Implement a circuit breaker (e.g. Resilience4j) in addition to the existing fallback, to avoid hammering a degraded translation service and fail fast instead.

- **Observability**: In this demo no structured logging has been added, which is a deliberate choice for a non-production-ready version. In a production context, observability would be a priority: structured logs, request tracing and error rate monitoring on each external API call would be essential to detect and diagnose issues quickly.

- **Authentication & rate limiting**: Add API key authentication and rate limiting on our own endpoints to prevent abuse.

- **Translation strategy**: The current implementation handles two translation types (Yoda and Shakespeare) with a straightforward conditional, which is perfectly adequate for this scope. If the number of translation models were to grow significantly, each with its own selection rules, fallback behaviour or API contract, the preferred approach would be to introduce a **Strategy Pattern**: each translator would implement a common interface, and a resolver would select the appropriate strategy at runtime based on the Pokémon's attributes. This would keep each translator isolated, independently testable and easy to extend without modifying existing logic.
