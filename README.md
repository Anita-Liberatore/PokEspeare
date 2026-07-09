# PokEspeare

REST API Spring Boot che restituisce la descrizione di un Pokémon riscritta in stile shakespeariano.

---

## Stack

- Java 17
- Spring Boot 3.3
- Maven
- Docker

---

## Avvio con Docker

### Prerequisiti
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installato e in esecuzione

### Comandi

```bash
# Clona il repository
git clone https://github.com/Anita-Liberatore/PokEspeare.git
cd PokEspeare

# Build e avvio
docker compose up --build
```

L'API sarà disponibile su `http://localhost:8080`.

Per fermare il container:

```bash
docker compose down
```

---

## Endpoint

| Metodo | Path | Descrizione |
|--------|------|-------------|
| GET | `/pokemon/{name}` | Restituisce la descrizione shakespeariana del Pokémon |

### Esempio

```bash
curl http://localhost:8080/pokemon/pikachu
```

---

## Avvio locale (senza Docker)

### Prerequisiti
- Java 17+
- Maven 3.9+

```bash
mvn spring-boot:run
```

---

## Test

```bash
mvn test
```
