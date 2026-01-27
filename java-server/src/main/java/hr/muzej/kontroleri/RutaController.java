package hr.muzej.kontroleri;

import hr.muzej.dto.OdgovorRuteDTO;
import hr.muzej.dto.ZahtjevRuteDTO;
import hr.muzej.servisi.RutaServis;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rute")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class RutaController {

    private final RutaServis rutaServis;
    
    @PostMapping("/izracunaj")
    public ResponseEntity<OdgovorRuteDTO> izracunajRutu(@Valid @RequestBody ZahtjevRuteDTO zahtjev) {
        log.info("Primljen zahtjev za rutu: {}", zahtjev);
        
        try {
            OdgovorRuteDTO odgovor = rutaServis.izracunajRutu(zahtjev);
            return ResponseEntity.ok(odgovor);
        } catch (IllegalArgumentException e) {
            log.warn("Neispravan zahtjev: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
