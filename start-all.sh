#!/bin/bash

echo "=== Pokretanje Muzeomat sustava ==="

if [ ! -f "./cpp-engine/tsp-engine" ]; then
    echo "Kompajliram C++ TSP engine..."
    cd cpp-engine
    make clean && make
    cd ..
fi

echo "Pokrecem Docker kontejnere..."
docker-compose up -d

echo "Cekam da PostgreSQL bude spreman..."
sleep 5

echo ""
echo "=== Status servisa ==="
docker-compose ps

echo ""
echo "Muzeomat sustav pokrenut!"
echo "- Java API: http://localhost:8080"
echo "- Python Admin: http://localhost:5000"
echo "- PostgreSQL: localhost:5432"