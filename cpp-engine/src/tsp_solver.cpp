#include "tsp_solver.hpp"
#include <iostream>
#include <set>
#include <algorithm>
#include <chrono>
#include <limits>

namespace muzej {

TSPSolver::TSPSolver(const Graf& g, int startIdx) : graf(g), start(startIdx) {}

RezultatTSP TSPSolver::nearestNeighbor() {
    RezultatTSP rez;
    int n = graf.getBrojProstorija();

    std::set<int> neposjecene;
    for (int i = 0; i < n; i++) {
        if (i != start) neposjecene.insert(i);
    }

    rez.ruta.push_back(start);
    int trenutna = start;

    while (!neposjecene.empty()) {
        int najbliza = -1;
        double minDist = std::numeric_limits<double>::max();

        for (int kandidat : neposjecene) {
            double d = graf.getUdaljenost(trenutna, kandidat);
            if (d < minDist) {
                minDist = d;
                najbliza = kandidat;
            }
        }

        rez.ruta.push_back(najbliza);
        neposjecene.erase(najbliza);
        trenutna = najbliza;
    }
//Vrati se na start
    rez.ruta.push_back(start);
    rez.udaljenost = graf.duljinaPuta(rez.ruta);
    return rez;
}

void TSPSolver::optimiziraj2Opt(RezultatTSP& rez) {
    std::cout << "[TSP] Pokrecem 2-opt optimizaciju...\n";

    bool poboljsano = true;
    int iter = 0;

    while (poboljsano && iter < 500) {
        poboljsano = false;
        iter++;

        int n = (int)rez.ruta.size();
        for (int i = 0; i < n - 2; i++) {
            for (int k = i + 2; k < n - 1; k++) {
                    //Provjeri jel zamjena bolja
                int a = rez.ruta[i];
                int b = rez.ruta[i + 1];
                int c = rez.ruta[k];
                int d = rez.ruta[k + 1];

                double trenutno = graf.getUdaljenost(a, b) + graf.getUdaljenost(c, d);
                double novo = graf.getUdaljenost(a, c) + graf.getUdaljenost(b, d);

                if (novo < trenutno) {
                        //Obrni segement
                    std::reverse(rez.ruta.begin() + i + 1, rez.ruta.begin() + k + 1);
                    poboljsano = true;
                }
            }
        }
    }

    rez.udaljenost = graf.duljinaPuta(rez.ruta);
}

RezultatTSP TSPSolver::rijesi() {
    auto pocetak = std::chrono::steady_clock::now();

    std::cout << "[TSP] Pocinjem optimizaciju za "
              << graf.getBrojProstorija() << " prostorija\n";
//1. Nearest Neighbor
    RezultatTSP rez = nearestNeighbor();
    std::cout << "[TSP] NN udaljenost: " << rez.udaljenost << "\n";
//2. 2-opt
    optimiziraj2Opt(rez);
    std::cout << "[TSP] 2-opt udaljenost: " << rez.udaljenost << "\n";

    auto kraj = std::chrono::steady_clock::now();
    rez.vrijemeMs = std::chrono::duration_cast<std::chrono::milliseconds>(kraj - pocetak).count();

    return rez;
}

} // namespace muzej
