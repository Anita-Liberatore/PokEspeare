# PokEspeare

A Spring Boot REST API that returns Pokémon descriptions rewritten in Shakespearean style.

---

## Tech Stack

- Java 17
- Spring Boot 3.3
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

## Endpoint

| Method | Path | Description |
|--------|------|-------------|
| GET | `/pokemon/{name}` | Returns the Shakespearean description of the given Pokémon |

### Example

```bash
curl http://localhost:8080/pokemon/pikachu
```

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
