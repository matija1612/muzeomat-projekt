package hr.muzej.kontroleri;
import hr.muzej.dto.ProstorijaDTO;
import hr.muzej.servisi.ProstorijaServis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
* Thymeleaf kontroler za admin panel
*/
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
private final ProstorijaServis servis;
/**
     * Prikazi login stranicu
     */
    @GetMapping("/login")
    public String login() {
        return "admin-login";
    }
    
    /**
     * Admin dashboard - popis prostorija
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        log.debug("Prikazujem admin dashboard");
        
        List<ProstorijaDTO> prostorije = servis.dohvatiSve();
        model.addAttribute("prostorije", prostorije);
        
        return "admin-dashboard";
    }
    
    /**
     * Forma za dodavanje nove prostorije
     */
    @GetMapping("/prostorije/nova")
    public String novaProstorija(Model model) {
        model.addAttribute("prostorija", new ProstorijaDTO());
        model.addAttribute("akcija", "nova");
        return "prostorija-form";
    }
    
    /**
     * Spremi novu prostoriju
     */
    @PostMapping("/prostorije/nova")
    public String spremiNovu(@ModelAttribute ProstorijaDTO dto) {
        log.info("Spremam novu prostoriju: {}", dto.getNaziv());
        
        servis.kreiraj(dto);
        
        return "redirect:/admin/dashboard";
    }
    
    /**
     * Forma za uredjivanje prostorije
     */
    @GetMapping("/prostorije/{id}/uredi")
    public String urediProstoriju(@PathVariable Long id, Model model) {
        ProstorijaDTO prostorija = servis.dohvatiPoId(id);
        
        model.addAttribute("prostorija", prostorija);
        model.addAttribute("akcija", "uredi");
        
        return "prostorija-form";
    }
    
    /**
     * Spremi izmjene
     */
    @PostMapping("/prostorije/{id}/uredi")
    public String spremiIzmjene(@PathVariable Long id, @ModelAttribute ProstorijaDTO dto) {
        log.info("Spremam izmjene za prostoriju ID: {}", id);
        
        servis.azuriraj(id, dto);
        
        return "redirect:/admin/dashboard";
    }
    
    /**
     * Obrisi prostoriju
     */
    @GetMapping("/prostorije/{id}/obrisi")
    public String obrisi(@PathVariable Long id) {
        log.warn("Brisem prostoriju ID: {}", id);
        
        servis.obrisi(id);
        
        return "redirect:/admin/dashboard";
    }
}