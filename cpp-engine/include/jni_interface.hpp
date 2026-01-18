#ifndef JNI_INTERFACE_HPP
#define JNI_INTERFACE_HPP
#ifdef __cplusplus
extern "C" {
#endif
// Strukture za JNA
typedef struct {
double x;
double y;
} KoordinateProstorije;
typedef struct {
int* ruta;
int duljina;
double udaljenost;
long vrijemeMs;
} RezultatRute;
// API funkcije
RezultatRute* izracunajRutu(
KoordinateProstorije* prostorije,
int n,
int start
);
void oslobodiRezultat(RezultatRute* rez);
#ifdef __cplusplus
}
#endif
#endif