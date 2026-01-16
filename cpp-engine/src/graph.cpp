#include "graph.hpp"

namespace muzej {

Graf::Graf(int brojProstorija) : n(brojProstorija) {
    prostorije.resize(n);
    udaljenosti.resize(n, std::vector<double>(n, 0.0));
}

void Graf::postaviProstoriju(int idx, double x, double y) {
    if (idx >= 0 && idx < n) {
        prostorije[idx] = Prostorija(x, y);
        izgradiMatricu();
    }
}

void Graf::izgradiMatricu() {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (i == j) udaljenosti[i][j] = 0.0;
            else udaljenosti[i][j] = prostorije[i].udaljenostDo(prostorije[j]);
        }
    }
}

double Graf::getUdaljenost(int i, int j) const {
    if (i >= 0 && i < n && j >= 0 && j < n) return udaljenosti[i][j];
    return 0.0;
}

double Graf::duljinaPuta(const std::vector<int>& put) const {
    if (put.size() < 2) return 0.0;

    double total = 0.0;
    for (size_t i = 0; i + 1 < put.size(); i++) {
        total += getUdaljenost(put[i], put[i + 1]);
    }
    return total;
}

} // namespace muzej
