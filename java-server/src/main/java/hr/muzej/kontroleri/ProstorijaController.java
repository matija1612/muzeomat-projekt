package hr.muzej.kontroleri;
import hr.muzej.dto.ProstorijaDTO;
import hr.muzej.servisi.ProstorijaServis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/prostorije")
@RequiredArgsConstructor
@Slf4j
public class ProstorijaController {
    
    private final ProstorijaServis servis;
    
    /**
     * GET /api/prostorije/aktivne
     * Dohvaca sve aktivne prostorije (za muzeomat kiosk)
     */
    @GetMapping("/aktivne")
    public ResponseEntity<List<ProstorijaDTO>> dohvatiAktivne() {
        log.debug("API poziv: GET /api/prostorije/aktivne");
        
        List<ProstorijaDTO> prostorije = servis.dohvatiAktivne();
        
        return ResponseEntity.ok(prostorije);
    }
    
    /**
     * GET /api/prostorije
     * Dohvaca sve prostorije (za admin panel)
 DAN 4 REST Kontroleri 2.5h
Git Commit: 
feat(clan-2): implementiraj REST API kontrolere
Fajl 14
java-server/src/main/java/hr/muzej/kontroleri/ProstorijaController.java
     */
    @GetMapping
    public ResponseEntity<List<ProstorijaDTO>> dohvatiSve() {
        log.debug("API poziv: GET /api/prostorije");
        
        List<ProstorijaDTO> prostorije = servis.dohvatiSve();
        
        return ResponseEntity.ok(prostorije);
    }
    
    /**
     * GET /api/prostorije/{id}
     * Dohvaca jednu prostoriju po ID-u
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProstorijaDTO> dohvatiPoId(@PathVariable Long id) {
        log.debug("API poziv: GET /api/prostorije/{}", id);
        
        ProstorijaDTO prostorija = servis.dohvatiPoId(id);
        
        return ResponseEntity.ok(prostorija);
    }
    
    /**
     * POST /api/prostorije
     * Kreira novu prostoriju
     */
    @PostMapping
    public ResponseEntity<ProstorijaDTO> kreiraj(@RequestBody ProstorijaDTO dto) {
        log.info("API poziv: POST /api/prostorije - {}", dto.getNaziv());
        
        ProstorijaDTO kreirana = servis.kreiraj(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(kreirana);
    }
    
    /**
     * PUT /api/prostorije/{id}
     * Azurira postojecu prostoriju
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProstorijaDTO> azuriraj(
            @PathVariable Long id,
            @RequestBody ProstorijaDTO dto) {
        
        log.info("API poziv: PUT /api/prostorije/{}", id);
        
        ProstorijaDTO azurirana = servis.azuriraj(id, dto);
        
        return ResponseEntity.ok(azurirana);
    }
    
    /**
     * DELETE /api/prostorije/{id}
     * Brise prostoriju
     */
@DeleteMapping("/{id}")
public ResponseEntity<Void> obrisi(@PathVariable Long id) {
log.warn("API poziv: DELETE /api/prostorije/{}", id);
servis.obrisi(id);
return ResponseEntity.noContent().build();
}
}