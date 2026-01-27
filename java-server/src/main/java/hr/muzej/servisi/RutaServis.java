package hr.muzej.servisi;

import hr.muzej.dto.DetaljiDTO;
import hr.muzej.dto.OdgovorRuteDTO;
import hr.muzej.dto.ZahtjevRuteDTO;
import hr.muzej.entiteti.Prostorija;
import hr.muzej.entiteti.Ruta;
import hr.muzej.repozitoriji.RutaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RutaServis {

    private final ProstorijaServis prostorijaServis;
    private final RutaRepo rutaRepo;
    private final TSPNativeServis tspServis;
    
    @Transactional
    public OdgovorRuteDTO izracunajRutu(ZahtjevRuteDTO zahtjev) {
        log.info("Izracunavam rutu za {} prostorija", zahtjev.getIdProstorija().size());
        
        if (zahtjev.getIdProstorija() == null || zahtjev.getIdProstorija().isEmpty()) {
            throw new IllegalArgumentException("Lista prostorija ne smije biti prazna");
        }
        
        if (zahtjev.getIdProstorija().size() < 2) {
            throw new IllegalArgumentException("Potrebne su barem 2 prostorije");
        }
        
        List<Prostorija> prostorije = prostorijaServis.dohvatiPoIdovima(
            zahtjev.getIdProstorija()
        );
        
        if (prostorije.size() != zahtjev.getIdProstorija().size()) {
            throw new IllegalArgumentException("Neke prostorije ne postoje u bazi");
        }
        
        log.debug("Prostorije dohvacene: {}", prostorije.size());
        
        List<Integer> optimiziranaRuta = tspServis.pozovi(prostorije);
        
        log.debug("TSP vratio rutu: {}", optimiziranaRuta);
        
        List<Long> putanjaId = optimiziranaRuta.stream()
            .map(idx -> prostorije.get(idx).getId())
            .collect(Collectors.toList());
        
        List<DetaljiDTO> detalji = kreirajDetalje(prostorije, optimiziranaRuta);
        
        double ukupnaUdaljenost = 0;
        int ukupnoVrijeme = 0;
        
        for (DetaljiDTO d : detalji) {
            if (d.getUdaljenostDoSljedece() != null) {
                ukupnaUdaljenost += d.getUdaljenostDoSljedece();
            }
            ukupnoVrijeme += d.getProsjecnoTrajanje();
        }
        
        log.info("Ruta izracunata: {} m, {} min", ukupnaUdaljenost, ukupnoVrijeme);
        
        spremiRutu(zahtjev, putanjaId, ukupnaUdaljenost);
        
        return OdgovorRuteDTO.builder()
            .putanja(putanjaId)
            .detalji(detalji)
            .ukupnaUdaljenost(ukupnaUdaljenost)
            .ukupnoVrijeme(ukupnoVrijeme)
            .vrijemeIzracuna(0L)
            .build();
    }
    
    private List<DetaljiDTO> kreirajDetalje(List<Prostorija> prostorije, List<Integer> ruta) {
        List<DetaljiDTO> detalji = new ArrayList<>();
        
        for (int i = 0; i < ruta.size(); i++) {
            int idx = ruta.get(i);
            Prostorija p = prostorije.get(idx);
            
            Double udaljenost = null;
            if (i < ruta.size() - 1) {
                int sljedeciIdx = ruta.get(i + 1);
                Prostorija sljedeca = prostorije.get(sljedeciIdx);
                udaljenost = p.udaljenostDo(sljedeca);
            }
            
            DetaljiDTO dto = DetaljiDTO.builder()
                .id(p.getId())
                .naziv(p.getNaziv())
                .brojKata(p.getBrojKata())
                .prosjecnoTrajanje(p.getProsjecnoTrajanje())
                .udaljenostDoSljedece(udaljenost)
                .koordinataX(p.getKoordinataX())
                .koordinataY(p.getKoordinataY())
                .build();
            
            detalji.add(dto);
        }
        
        return detalji;
    }
    
    private void spremiRutu(ZahtjevRuteDTO zahtjev, List<Long> putanja, double udaljenost) {
        String putanjaStr = putanja.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));
        
        Ruta ruta = Ruta.builder()
            .muzeomatId(zahtjev.getMuzeomatId())
            .putanja(putanjaStr)
            .ukupnaUdaljenost(udaljenost)
            .build();
        
        rutaRepo.save(ruta);
        log.debug("Ruta spremljena u bazu");
    }
}
