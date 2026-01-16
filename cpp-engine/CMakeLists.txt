cmake_minimum_required(VERSION 3.10)
project(muzej_tsp VERSION 1.0.0)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
# Compiler flags
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -O3")
include_directories(${PROJECT_SOURCE_DIR}/include)
set(SOURCES
src/graph.cpp
src/tsp_solver.cpp
src/jni_interface.cpp
)
add_library(muzej_tsp SHARED ${SOURCES})
set_target_properties(muzej_tsp PROPERTIES
LIBRARY_OUTPUT_DIRECTORY "${PROJECT_SOURCE_DIR}/build"
)
message(STATUS "Muzej TSP Engine - C++17")