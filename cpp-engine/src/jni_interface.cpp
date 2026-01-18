#include "jni_interface.hpp"
#include "graph.hpp"
#include "tsp_solver.hpp"
#include <iostream>
#include <cstring>
extern "C" {
RezultatRute* izracunajRutu(
    KoordinateProstorije* prostorije,
    int n,
    int start
) {
    try {
        if (!prostorije || n <= 0) {
            std::cerr << "[JNI] Invalid input\n";
            return nullptr;
        }
        
        std::cout << "[JNI] Pozvan TSP za " << n << " prostorija\n";
        
        // Kreiraj graf
        muzej::Graf graf(n);
        for (int i = 0; i < n; i++) {
            graf.postaviProstoriju(i, prostorije[i].x, prostorije[i].y);
        }
        
        // Rijesi TSP
        muzej::TSPSolver solver(graf, start);
        muzej::RezultatTSP tspRez = solver.rijesi();
        
        // Alociraj rezultat
        RezultatRute* rez = new RezultatRute();
        rez->duljina = tspRez.ruta.size();
        rez->ruta = new int[rez->duljina];
        std::memcpy(rez->ruta, tspRez.ruta.data(), rez->duljina * sizeof(int));
        rez->udaljenost = tspRez.udaljenost;
        rez->vrijemeMs = tspRez.vrijemeMs;
        
        std::cout << "[JNI] TSP gotov, udaljenost=" << rez->udaljenost << "\n";
        
        return rez;
        
    } catch (const std::exception& e) {
        std::cerr << "[JNI] Exception: " << e.what() << "\n";

return nullptr;
}
}
void oslobodiRezultat(RezultatRute* rez) {
if (rez) {
if (rez->ruta) delete[] rez->ruta;
delete rez;
}
}
} // extern "C"