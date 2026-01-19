package hr.muzej.servisi;
import hr.muzej.dto.ProstorijaDTO;
import hr.muzej.entiteti.Prostorija;
import hr.muzej.exception.ResourceNotFoundException;
import hr.muzej.repozitoriji.ProstorijaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class ProstorijaServis {
    
    private final ProstorijaRepo repo;
    
    /**
     * Dohvati sve aktivne prostorije
     */
    @Transactional(readOnly = true)
    public List<ProstorijaDTO> dohvatiAktivne() {
        log.debug("Dohvacam sve aktivne prostorije");
        
        List<Prostorija> prostorije = repo.findByAktivnaTrue();
        
        return prostorije.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Dohvati sve prostorije (za admin panel)
     */
    @Transactional(readOnly = true)
    public List<ProstorijaDTO> dohvatiSve() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Dohvati prostoriju po ID-u
     */
    @Transactional(readOnly = true)
    public ProstorijaDTO dohvatiPoId(Long id) {
        Prostorija p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prostorija ne postoji"));
        
        return toDTO(p);
    }
    
    /**
     * Kreiraj novu prostoriju
     */
    @Transactional
    public ProstorijaDTO kreiraj(ProstorijaDTO dto) {
        log.info("Kreiram novu prostoriju: {}", dto.getNaziv());
        
        Prostorija p = Prostorija.builder()
                .naziv(dto.getNaziv())
                .opis(dto.getOpis())
                .brojKata(dto.getBrojKata())
                .koordinataX(dto.getKoordinataX())
                .koordinataY(dto.getKoordinataY())
                .prosjecnoTrajanje(dto.getProsjecnoTrajanje())
                .aktivna(true)
                .build();
        
        Prostorija spremljena = repo.save(p);
        return toDTO(spremljena);
    }
    
    /**
     * Azuriraj postojecu prostoriju
     */
    @Transactional
    public ProstorijaDTO azuriraj(Long id, ProstorijaDTO dto) {
        log.info("Azuriram prostoriju ID: {}", id);
        
        Prostorija p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prostorija ne postoji"));
        
        // Azuriraj polja
        p.setNaziv(dto.getNaziv());
        p.setOpis(dto.getOpis());
        p.setBrojKata(dto.getBrojKata());
        p.setKoordinataX(dto.getKoordinataX());
        p.setKoordinataY(dto.getKoordinataY());
        p.setProsjecnoTrajanje(dto.getProsjecnoTrajanje());
        p.setAktivna(dto.getAktivna());
        
        Prostorija azurirana = repo.save(p);
        return toDTO(azurirana);
    }
    
    /**
     * Obrisi prostoriju
     */
    @Transactional
    public void obrisi(Long id) {
        log.warn("Brisem prostoriju ID: {}", id);
        
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Prostorija ne postoji");
        }
        
        repo.deleteById(id);
    }
    
    /**
     * Dohvati prostorije po ID-ovima (za TSP)
     */
    @Transactional(readOnly = true)
    public List<Prostorija> dohvatiPoIdovima(List<Long> ids) {
        return repo.findAllById(ids);
    }
    
    // Helper - konverzija u DTO
    private ProstorijaDTO toDTO(Prostorija p) {
        return ProstorijaDTO.builder()
                .id(p.getId())
                .naziv(p.getNaziv())
                .opis(p.getOpis())
                .brojKata(p.getBrojKata())
                .koordinataX(p.getKoordinataX())
                .koordinataY(p.getKoordinataY())
                .prosjecnoTrajanje(p.getProsjecnoTrajanje())
                .aktivna(p.getAktivna())
                .build();
    }
}
