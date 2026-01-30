FROM alpine:latest

# Install build tools
RUN apk add --no-cache gcc g++ cmake make

WORKDIR /app

# Copy C++ source
COPY . .

# Build TSP library (if CMakeLists.txt exists)
RUN mkdir -p build && cd build && \
    cmake .. && \
    make || echo "Build skipped - no CMakeLists.txt"

# Make library accessible (if exists)
RUN cp build/libtsp.so /usr/lib/libtsp.so 2>/dev/null || echo "libtsp.so not built yet"

CMD ["tail", "-f", "/dev/null"]
