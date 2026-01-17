#ifndef TSP_SOLVER_HPP
#define TSP_SOLVER_HPP

#include "graph.hpp"
#include <vector>

namespace muzej {

struct RezultatTSP {
    std::vector<int> ruta;
    double udaljenost;
    long vrijemeMs;
    RezultatTSP() : udaljenost(0), vrijemeMs(0) {}
};

class TSPSolver {
private:
    const Graf& graf;
    int start;

    RezultatTSP nearestNeighbor();
    void optimiziraj2Opt(RezultatTSP& rez);

public:
    TSPSolver(const Graf& g, int startIdx);
    RezultatTSP rijesi();
};

} // namespace muzej

#endif
