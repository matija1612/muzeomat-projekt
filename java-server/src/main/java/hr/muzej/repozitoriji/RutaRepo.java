package hr.muzej.repozitoriji;
import hr.muzej.entiteti.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RutaRepo extends JpaRepository<Ruta, Long> {
// Dohvati rute po muzeomat ID-u
List<Ruta> findByMuzeomatId(String muzeomatId);
// Dohvati rute u vremenskom periodu (za statistiku)
List<Ruta> findByVrijemeKreiranjaAfter(LocalDateTime vrijeme);
}
