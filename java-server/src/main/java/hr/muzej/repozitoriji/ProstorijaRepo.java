package hr.muzej.repozitoriji;
import hr.muzej.entiteti.Prostorija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ProstorijaRepo extends JpaRepository<Prostorija, Long> {
// Dohvati sve aktivne prostorije
List<Prostorija> findByAktivnaTrue();
// Dohvati prostorije po katu
List<Prostorija> findByBrojKata(Integer kat);
}
