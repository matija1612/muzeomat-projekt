#ifndef GRAPH_HPP
#define GRAPH_HPP

#include <vector>
#include <cmath>

namespace muzej {

struct Prostorija {
    double x;
    double y;

    Prostorija() : x(0), y(0) {}
    Prostorija(double _x, double _y) : x(_x), y(_y) {}

    double udaljenostDo(const Prostorija& druga) const {
        double dx = x - druga.x;
        double dy = y - druga.y;
        return std::sqrt(dx * dx + dy * dy);
    }
};

class Graf {
private:
    int n;
    std::vector<Prostorija> prostorije;
    std::vector<std::vector<double>> udaljenosti;

    void izgradiMatricu();

public:
    Graf(int brojProstorija);

    void postaviProstoriju(int idx, double x, double y);

    int getBrojProstorija() const { return n; }

    double getUdaljenost(int i, int j) const;

    double duljinaPuta(const std::vector<int>& put) const;
};

} // namespace muzej

#endif
