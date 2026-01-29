# Muzej System - Python Admin Panel


### Lokalno pokretanje

#### Preduvjeti
- Python 3.11+
- Docker Desktop
- 2GB RAM

#### Setup

```bash
# Clone projekta (kada bude na GitHub-u)
git clone https://github.com/vas-tim/muzej-system.git
cd muzej-system/python-admin

# Virtual environment
python3 -m venv venv
source venv/bin/activate  # macOS/Linux
# venv\Scripts\activate   # Windows

# Install dependencies
pip install -r requirements.txt

# Run app
python run.py
```

### Login credentials

| Username | Password |
|----------|----------|
| admin | password123 |

### Docker pokretanje

```bash
# Root projekta
cd muzej-system

# Start all services
docker-compose up -d

# Check status
docker-compose ps

# Logs
docker-compose logs python-admin
```

### Testirano

- ✅ Python Flask Admin
- ✅ SQLite database
- ✅ PostgreSQL (Docker)
- ✅ Redis (Docker)
- ✅ CRUD operacije
- ✅ Authentication

### Čeka integraciju

- ❌ Java Backend (Član 2)
- ❌ C++ TSP Engine (Član 3)
- ❌ JavaFX Client (Član 3)

### Screenshots

(vidi docs/screenshots/member4/)
