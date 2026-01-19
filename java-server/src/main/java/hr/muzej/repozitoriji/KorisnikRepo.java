package hr.muzej.repozitoriji;
import hr.muzej.entiteti.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface KorisnikRepo extends JpaRepository<Korisnik, Long> {
// Pronadi korisnika po korisnickom imenu
Optional<Korisnik> findByKorisnickoIme(String korisnickoIme);
}
