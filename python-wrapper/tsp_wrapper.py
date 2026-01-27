#!/usr/bin/env python3

import subprocess
import sys
import os
from typing import List, Tuple
import argparse


class TSPWrapper:
    
    def __init__(self, executable_path: str = "./tsp-engine"):
        self.executable_path = executable_path
        
        if not os.path.exists(executable_path):
            print(f"UPOZORENJE: TSP executable nije pronaden: {executable_path}")
    
    def solve(self, distance_matrix: List[List[float]]) -> Tuple[List[int], float]:
        n = len(distance_matrix)
        
        if n < 2:
            raise ValueError("Potrebne su barem 2 tocke")
        
        for row in distance_matrix:
            if len(row) != n:
                raise ValueError("Matrica mora biti NxN")
        
        input_str = self._format_input(distance_matrix)
        
        try:
            result = subprocess.run(
                [self.executable_path],
                input=input_str,
                capture_output=True,
                text=True,
                timeout=30
            )
            
            if result.returncode != 0:
                print(f"TSP engine greska: {result.stderr}")
                return self._fallback_route(n), 0.0
            
            route = self._parse_output(result.stdout)
            total_distance = self._calculate_distance(route, distance_matrix)
            
            return route, total_distance
            
        except FileNotFoundError:
            print(f"TSP executable nije pronaden: {self.executable_path}")
            return self._fallback_route(n), 0.0
        except subprocess.TimeoutExpired:
            print("TSP engine timeout")
            return self._fallback_route(n), 0.0
    
    def _format_input(self, matrix: List[List[float]]) -> str:
        n = len(matrix)
        lines = [str(n)]
        
        for row in matrix:
            lines.append(" ".join(f"{x:.2f}" for x in row))
        
        return "\n".join(lines) + "\n"
    
    def _parse_output(self, output: str) -> List[int]:
        route = []
        for line in output.strip().split("\n"):
            for part in line.split():
                try:
                    route.append(int(part))
                except ValueError:
                    pass
        return route
    
    def _fallback_route(self, n: int) -> List[int]:
        return list(range(n))
    
    def _calculate_distance(self, route: List[int], matrix: List[List[float]]) -> float:
        total = 0.0
        for i in range(len(route) - 1):
            total += matrix[route[i]][route[i + 1]]
        return total


def main():
    parser = argparse.ArgumentParser(description="Python wrapper za TSP C++ engine")
    parser.add_argument("--executable", "-e", default="./tsp-engine", help="Putanja do TSP executable-a")
    parser.add_argument("--test", "-t", action="store_true", help="Pokreni test s primjerom")
    
    args = parser.parse_args()
    
    wrapper = TSPWrapper(args.executable)
    
    if args.test:
        print("Pokrecem test...")
        
        matrix = [
            [0, 1, 1.41, 1],
            [1, 0, 1, 1.41],
            [1.41, 1, 0, 1],
            [1, 1.41, 1, 0]
        ]
        
        print(f"Matrica udaljenosti ({len(matrix)}x{len(matrix)}):")
        for row in matrix:
            print("  " + " ".join(f"{x:5.2f}" for x in row))
        
        route, distance = wrapper.solve(matrix)
        
        print(f"\nOptimalna ruta: {route}")
        print(f"Ukupna udaljenost: {distance:.2f}")
    else:
        print("Unesite velicinu matrice (n):")
        n = int(input())
        
        print(f"Unesite {n} redaka s {n} vrijednosti:")
        matrix = []
        for _ in range(n):
            row = [float(x) for x in input().split()]
            matrix.append(row)
        
        route, distance = wrapper.solve(matrix)
        
        print(f"\nRuta: {' '.join(map(str, route))}")
        print(f"Udaljenost: {distance:.2f}")


if __name__ == "__main__":
    main()
