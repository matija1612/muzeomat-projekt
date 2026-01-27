#!/bin/bash

echo "=== TSP Wrapper Test ==="

if [ ! -f "./tsp-engine" ]; then
    echo "GRESKA: tsp-engine nije pronaden u trenutnom direktoriju"
    echo "Potrebno je kompajlirati C++ engine"
    exit 1
fi

echo "Pokrecem Python wrapper test..."
python3 tsp_wrapper.py --test

echo ""
echo "=== Test zavrsen ==="