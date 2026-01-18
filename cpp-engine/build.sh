#!/bin/bash
set -e

echo "Building Muzej TSP Engine..."

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
BUILD_DIR="$SCRIPT_DIR/build"

rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"
cd "$BUILD_DIR"

cmake ..
make -j"$(nproc)"

# Provjeri output (Linux: .so). Ako kasnije gradite na Windowsu bit će .dll
if [ -f "libmuzej_tsp.so" ]; then
  echo "✓ Build success!"
  echo "Library: $(pwd)/libmuzej_tsp.so"
else
  echo "✗ Build failed! libmuzej_tsp.so not found."
  exit 1
fi
