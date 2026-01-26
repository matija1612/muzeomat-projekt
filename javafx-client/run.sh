#!/bin/bash
echo "Running JavaFX Client..."
# Check C++ library
if [ ! -f "/usr/lib/libmuzej_tsp.so" ]; then
echo "Warning: C++ library not found"
echo "Copying from cpp-engine/build..."
sudo cp ../cpp-engine/build/libmuzej_tsp.so /usr/lib/
fi
export LD_LIBRARY_PATH=/usr/lib:$LD_LIBRARY_PATH
mvn javafx:run