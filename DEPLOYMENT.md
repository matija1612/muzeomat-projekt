# Deployment Upute

## Preduvjeti

- Docker i Docker Compose
- JDK 21
- Python 3.11+
- g++ kompajler (za C++ engine)

## Koraci za deploy

### 1. Kloniraj repozitorij

```bash
git clone https://github.com/matija1612/muzeomat-projekt.git
cd muzeomat-projekt
```

### 2. Kompajliraj C++ TSP engine

```bash
cd cpp-engine
make
cd ..
```

### 3. Pokreni sustav

```bash
./start-all.sh
```

Ili rucno:

```bash
docker-compose up -d
```

### 4. Provjeri status

```bash
docker-compose ps
```

## Servisi

| Servis | Port | Opis |
|--------|------|------|
| java-server | 8080 | Spring Boot API |
| python-admin | 5000 | Flask Admin Panel |
| postgres | 5432 | PostgreSQL baza |

## API Endpointi

### Prostorije
- `GET /api/prostorije` - Lista svih prostorija
- `POST /api/prostorije` - Nova prostorija
- `GET /api/prostorije/{id}` - Dohvati prostoriju

### Rute
- `POST /api/rute/izracunaj` - Izracunaj optimalnu rutu
- `GET /api/rute/health` - Health check

## Zaustavljanje

```bash
./stop-all.sh
```

Ili:

```bash
docker-compose down
```

## Troubleshooting

### Baza se ne pokrece
```bash
docker-compose logs postgres
```

### Java server ne radi
```bash
docker-compose logs java-server
```

### TSP engine problem
Provjeri je li kompajliran:
```bash
ls -la cpp-engine/tsp-engine
```
